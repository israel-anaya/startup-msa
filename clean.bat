echo OFF
echo -----------------------------------------------------------------
echo MSA Startup Framework
echo -----------------------------------------------------------------

call mvn -f startup-msa-config-server/pom.xml clean
call mvn -f startup-msa-discovery-server/pom.xml clean
call mvn -f startup-msa-edge-server/pom.xml clean


