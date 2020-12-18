call kubectl apply -f target/kubernetes/startup-msa-edge-server-configmap.yml
call kubectl apply -f target/kubernetes/startup-msa-edge-server-deployment.yml
call kubectl apply -f target/kubernetes/startup-msa-edge-server-service.yml