echo -----------------------------------------------------------------
echo Startup Framework MSA Kubernetes Files
set DOCKER_HOST=tcp://localhost:2375
call mvn clean package k8s:resource