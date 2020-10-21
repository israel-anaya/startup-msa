package org.startupframework.msa.security.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.sql.DataSource;

import org.infinispan.configuration.parsing.ConfigurationBuilderHolder;
import org.infinispan.configuration.parsing.ParserRegistry;
import org.infinispan.manager.DefaultCacheManager;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyContextParameters;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.startupframework.msa.security.keycloak.EmbeddedKeycloakApplication;
import org.startupframework.msa.security.keycloak.EmbeddedKeycloakServer;
import org.startupframework.msa.security.support.DynamicJndiContextFactoryBuilder;
import org.startupframework.msa.security.support.KeycloakUndertowRequestFilter;
import org.startupframework.msa.security.support.SpringBootConfigProvider;
import org.startupframework.msa.security.support.SpringBootPlatform;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class EmbeddedKeycloakConfig {

	public EmbeddedKeycloakConfig() {
		int i = 0;
	}
	
    @Bean
    @ConditionalOnMissingBean(name = "embeddedKeycloakServer")
    protected EmbeddedKeycloakServer embeddedKeycloakServer(ServerProperties serverProperties) {
        return new EmbeddedKeycloakServer(serverProperties);
    }

    @Bean
    @ConditionalOnMissingBean(name = "springBootPlatform")
    protected SpringBootPlatform springBootPlatform() {
        return new SpringBootPlatform();
    }

    @Bean
    @ConditionalOnMissingBean(name = "springBootConfigProvider")
    protected SpringBootConfigProvider springBootConfigProvider(KeycloakProperties keycloakProperties) {
        return new SpringBootConfigProvider(keycloakProperties);
    }

    @Bean
    @ConditionalOnMissingBean(name = "springBeansJndiContextFactory")
    protected DynamicJndiContextFactoryBuilder springBeansJndiContextFactory(DataSource dataSource, DefaultCacheManager cacheManager) {
        return new DynamicJndiContextFactoryBuilder(dataSource, cacheManager);
    }

    @Bean
    @ConditionalOnMissingBean(name = "keycloakInfinispanCacheManager")
    protected DefaultCacheManager keycloakInfinispanCacheManager(KeycloakCustomProperties customProperties) throws Exception {

        KeycloakCustomProperties.Infinispan infinispan = customProperties.getInfinispan();
        Resource configLocation = infinispan.getConfigLocation();
        log.info("Using infinispan configuration from {}", configLocation.getURI());

        ConfigurationBuilderHolder configBuilder = new ParserRegistry().parse(configLocation.getURL());
        DefaultCacheManager defaultCacheManager = new DefaultCacheManager(configBuilder, false);
        defaultCacheManager.start();
        return defaultCacheManager;
    }

    @Bean
    @ConditionalOnMissingBean(name = "keycloakJaxRsApplication")
    protected ServletRegistrationBean<HttpServlet30Dispatcher> keycloakJaxRsApplication(KeycloakCustomProperties customProperties) {

        initKeycloakEnvironmentFromProfiles();

        ServletRegistrationBean<HttpServlet30Dispatcher> servlet = new ServletRegistrationBean<>(new HttpServlet30Dispatcher());
        servlet.addInitParameter("javax.ws.rs.Application", EmbeddedKeycloakApplication.class.getName());

        servlet.addInitParameter("resteasy.allowGzip", "true");
        servlet.addInitParameter("keycloak.embedded", "true");
        servlet.addInitParameter(ResteasyContextParameters.RESTEASY_EXPAND_ENTITY_REFERENCES, "false");
        servlet.addInitParameter(ResteasyContextParameters.RESTEASY_SECURE_PROCESSING_FEATURE, "true");
        servlet.addInitParameter(ResteasyContextParameters.RESTEASY_DISABLE_DTDS, "true");
        servlet.addInitParameter(ResteasyContextParameters.RESTEASY_SERVLET_MAPPING_PREFIX, customProperties.getServer().getKeycloakPath());
        servlet.addInitParameter(ResteasyContextParameters.RESTEASY_USE_CONTAINER_FORM_PARAMS, "false");
        servlet.addInitParameter(ResteasyContextParameters.RESTEASY_DISABLE_HTML_SANITIZER, "true");
        servlet.addUrlMappings(customProperties.getServer().getKeycloakPath() + "/*");

        servlet.setLoadOnStartup(2);
        servlet.setAsyncSupported(true);

        return servlet;
    }

    private void initKeycloakEnvironmentFromProfiles() {

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("profile.properties")) {

            if (in == null) {
                log.info("Could not find profile.properties on classpath.");
                return;
            }

            Properties profile = new Properties();
            profile.load(in);

            log.info("Found profile.properties on classpath.");
            String profilePrefix = "keycloak.profile.";
            for (Object key : profile.keySet()) {
                String value = (String) profile.get(key);
                String featureName = key.toString().toLowerCase();
                String currentValue = System.getProperty(profilePrefix + featureName);
                if (currentValue == null) {
                    System.setProperty(profilePrefix + featureName, value);
                }
            }
        } catch (IOException ioe) {
            log.warn("Could not read profile.properties.", ioe);
        }
    }

    @Bean
    @ConditionalOnMissingBean(name = "keycloakSessionManagement")
    protected FilterRegistrationBean<Filter> keycloakSessionManagement(KeycloakCustomProperties customProperties) {

        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
        filter.setName("Keycloak Session Management");
        filter.setFilter(new KeycloakUndertowRequestFilter());
        filter.addUrlPatterns(customProperties.getServer().getKeycloakPath() + "/*");

        return filter;
    }

}
