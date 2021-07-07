echo -----------------------------------------------------------------
echo Startup Framework MSA Docker Images
set DOCKER_HOST=tcp://localhost:2375
call mvn clean package k8s:build k8s:push