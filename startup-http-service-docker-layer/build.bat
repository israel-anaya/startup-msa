@ECHO OFF

SET DOCKER_FILE=Dockerfile
SET DOCKER_REGISTRY=localhost:5000
SET GROUPID=startupframework
SET COMPONENT=http-service
SET VERSION=1.0.2.RELEASE
SET DOCKER_TAG=%GROUPID%/%COMPONENT%:%VERSION%
REM SET DOCKER_TAG=%DOCKER_REGISTRY%/%GROUPID%/%COMPONENT%:%VERSION%

ECHO -----------------------------------------------------------------
ECHO Builing Enviroment.... 

ECHO DOCKER_FILE = %DOCKER_FILE%
ECHO GROUPID = %GROUPID%
ECHO COMPONENT = %COMPONENT%
ECHO VERSION = %VERSION%
ECHO DOCKER_TAG = %DOCKER_TAG%
ECHO -----------------------------------------------------------------

call DOCKER build -f %DOCKER_FILE% -t %DOCKER_TAG% .

call DOCKER push %DOCKER_TAG%