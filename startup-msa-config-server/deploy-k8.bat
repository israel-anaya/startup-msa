call kubectl apply -f target/kubernetes/startup-msa-config-server-configmap.yml
call kubectl apply -f target/kubernetes/startup-msa-config-server-deployment.yml
call kubectl apply -f target/kubernetes/startup-msa-config-server-service.yml