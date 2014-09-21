@echo off
@setlocal
date /t
time /t
set JAVA_HOME=C:\software\bea\jdk141_05
set J2EE_HOME=C:\software\bea\weblogic81\server
set ANT_HOME=C:\develop\apache-ant-1.5.3

set PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin

set CLASSPATH=%J2EE_HOME%\lib\weblogic.jar;C:\ljdevelop\ljboss\webapp\classes\lib\json-lib-2.4-jdk14.jar;C:\ljdevelop\ljboss\webapp\classes\lib\jxl.jar;C:\ljdevelop\ljboss\webapp\classes\lib\poi-3.0.1-FINAL-20070705.jar;C:\ljdevelop\ljboss\webapp\classes;C:\ljdevelop\ljboss\webapp\classes\lib\log4j-1.2.11.jar;%JAVA_HOME%\jre\lib\rt.jar;%J2EE_HOME%\lib\j2ee.jar;%JAVA_HOME%\lib\tools.jar;C:\ljdevelop\ljboss\webapp\classes\lib\axis.jar;C:\ljdevelop\ljboss\webapp\classes\lib\axis-ant.jar;C:\ljdevelop\ljboss\webapp\classes\lib\commons-discovery-0.2.jar;C:\ljdevelop\ljboss\webapp\classes\lib\commons-logging-1.0.4.jar;C:\ljdevelop\ljboss\webapp\classes\lib\jaxrpc.jar;C:\ljdevelop\ljboss\webapp\classes\lib\saaj.jar;C:\ljdevelop\ljboss\webapp\classes\lib\wsdl4j-1.5.1.jar;C:\software\bea\weblogic81\server\lib\webservices.jar


if "%1" == "file" GOTO BUILD
if "%1" == "clean" GOTO CLEAN
if "%1" == "nowar" GOTO NOWAR
if "%1" == "deploy" GOTO DEPLOY
if "%1" == "undeploy" GOTO UNDEPLOY
:BUILD
echo -----------------BUILD FILE------------------
call ant -Dj2ee.dir=%J2EE_HOME% -buildfile "buildfile.xml"
GOTO END
:CLEAN
echo -----------------CLEAN FILE------------
call ant -Dj2ee.dir=%J2EE_HOME% -buildfile "buildclean.xml"
GOTO END
:NOWAR
echo -----------------BUILD NO WAR FILE------------
call ant -Dj2ee.dir=%J2EE_HOME% -buildfile "nowar.xml"
GOTO END
:DEPLOY
echo -----------------DEPLOY FILE------------
#java  -classpath C:\software\bea\weblogic81\server\lib\weblogic_sp.jar;C:\software\bea\weblogic81\server\lib\weblogic.jar;C:\software\bea\weblogic81\server\lib\webservices.jar; weblogic.Deployer  -user yangchen  -adminurl http://127.0.0.1:7001  -password yangchen  -activate  -name OSS -upload  -source D:\CVS\boss_p5.3_gh_cms.4\sourcecode\WebApp\OSS.ear -targets myserver
#GOTO END
#:UNDEPLOY
#echo -----------------UNDEPLOY FILE------------
#java  -classpath C:\software\bea\weblogic81\server\lib\weblogic_sp.jar;C:\software\bea\weblogic81\server\lib\weblogic.jar;C:\software\bea\weblogic81\server\lib\webservices.jar; weblogic.Deployer  -user yangchen  -adminurl http://127.0.0.1:7001  -password yangchen  -remove  -name OSS
#GOTO END

:END
date /t
time /t
@endlocal
