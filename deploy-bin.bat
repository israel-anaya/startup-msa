call copy .\startup-msa-authorization-server\target\kubernetes\*.yml ..\startup-msa-bin\security /y
call copy .\startup-msa-admin-server\target\kubernetes\*.yml ..\startup-msa-bin\spring-cloud /y
call copy .\startup-msa-config-server\target\kubernetes\*.yml ..\startup-msa-bin\spring-cloud /y
call copy .\startup-msa-discovery-server\target\kubernetes\*.yml ..\startup-msa-bin\spring-cloud /y
call copy .\startup-msa-edge-server\target\kubernetes\*.yml ..\startup-msa-bin\spring-cloud /y
