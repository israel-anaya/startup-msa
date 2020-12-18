call kubectl delete -f target/kubernetes/startup-msa-authorization-server-configmap.yml
call kubectl delete -f target/kubernetes/startup-msa-authorization-server-deployment.yml
call kubectl delete -f target/kubernetes/startup-msa-authorization-server-service.yml