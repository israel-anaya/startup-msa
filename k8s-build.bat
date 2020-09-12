echo -----------------------------------------------------------------
echo Startup Framework MSA Kubernetes Files

call mvn -f startup-msa-config-server/pom.xml clean package k8s:build k8s:push k8s:resource
call mvn -f startup-msa-discovery-server/pom.xml clean package k8s:build k8s:push k8s:resource
call mvn -f startup-msa-edge-server/pom.xml clean package k8s:build k8s:push k8s:resource