echo OFF
echo -----------------------------------------------------------------
echo MSA Startup Framework
echo -----------------------------------------------------------------

call mvn -f startup-msa-config-server/pom.xml clean package
call mvn -f startup-msa-discovery-server/pom.xml clean package
call mvn -f startup-msa-edge-server/pom.xml clean package
call mvn -f startup-msa-admin-server/pom.xml clean package
