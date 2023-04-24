@echo off

set ANNOTATION_PROCESSORS_DIR=C:\Dev\src\jeyzer\jeyzer-annotation-processors
set ANNOTATIONS_DIR=C:\Dev\src\jeyzer\jeyzer-annotations
set ANNOTATIONS_VERSION=3.0
set DEMO_DIR=C:\Dev\src\jeyzer\jeyzer-demo

rem reset MAVEN_OPTS
set MAVEN_OPTS=

rem Compile annotations - Optional
cd %ANNOTATIONS_DIR%
call mvn install
call mvn install:install-file -Dfile=%ANNOTATION_PROCESSORS_DIR%\target\jeyzer-annotation-processors-%ANNOTATIONS_VERSION%.jar -DpomFile=%ANNOTATION_PROCESSORS_DIR%\pom.xml

cd %ANNOTATION_PROCESSORS_DIR%

rem Compile annotation processor
call mvn clean install

rem Push it on mvn repository
call mvn install:install-file -Dfile=%ANNOTATION_PROCESSORS_DIR%\target\jeyzer-annotation-processors-%ANNOTATIONS_VERSION%.jar -DpomFile=%ANNOTATION_PROCESSORS_DIR%\pom.xml

rem Move to demo project
cd %DEMO_DIR%

rem Clean the demo project
call mvn clean

rem Set Maven in debug mode
set MAVEN_OPTS=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005

rem Compile the demo to debug the annotation module
call mvn compile

rem Reset path
cd %ANNOTATION_PROCESSORS_DIR%

rem reset MAVEN_OPTS
set MAVEN_OPTS=