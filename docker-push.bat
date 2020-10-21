echo -----------------------------------------------------------------
echo Startup Framework MSA Docker Images

call mvn clean package k8s:build k8s:push