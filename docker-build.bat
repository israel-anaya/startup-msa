echo -----------------------------------------------------------------
echo Startup Framework MSA Docker Images

call mvn -f startup-msa-config-server/pom.xml clean package k8s:build
call mvn -f startup-msa-discovery-server/pom.xml clean package k8s:build 
call mvn -f startup-msa-edge-server/pom.xml clean package k8s:build