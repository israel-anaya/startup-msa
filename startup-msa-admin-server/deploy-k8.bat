call kubectl apply -f target/kubernetes/startup-msa-admin-server-configmap.yml
call kubectl apply -f target/kubernetes/startup-msa-admin-server-deployment.yml
call kubectl apply -f target/kubernetes/startup-msa-admin-server-service.yml