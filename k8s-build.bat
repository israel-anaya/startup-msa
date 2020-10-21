echo -----------------------------------------------------------------
echo Startup Framework MSA Kubernetes Files

call mvn clean package k8s:build k8s:push k8s:resource