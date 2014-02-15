@echo off
@setlocal
date /t
time /t
set JAVA_HOME=C:\bea\jdk141_05
set J2EE_HOME=C:\bea\weblogic81\server
set ANT_HOME=D:\ant

set PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin

set CLASSPATH=%J2EE_HOME%\lib\weblogic.jar;E:\boss_p5.9\sourcecode\WebApp\classes\lib\jxl.jar;E:\boss_p5.9\sourcecode\WebApp\classes\lib\poi-3.0.1-FINAL-20070705.jar;E:\boss_p5.9\sourcecode\WebApp\classes;E:\boss_p5.9\sourcecode\WebApp\classes\lib\log4j-1.2.11.jar;%JAVA_HOME%\jre\lib\rt.jar;%J2EE_HOME%\lib\j2ee.jar;%JAVA_HOME%\lib\tools.jar;E:\boss_p5.9\sourcecode\WebApp\classes\lib\axis.jar;E:\boss_p5.9\sourcecode\WebApp\classes\lib\axis-ant.jar;E:\boss_p5.9\sourcecode\WebApp\classes\lib\commons-discovery-0.2.jar;E:\boss_p5.9\sourcecode\WebApp\classes\lib\commons-logging-1.0.4.jar;E:\boss_p5.9\sourcecode\WebApp\classes\lib\jaxrpc.jar;E:\boss_p5.9\sourcecode\WebApp\classes\lib\saaj.jar;E:\boss_p5.9\sourcecode\WebApp\classes\lib\wsdl4j-1.5.1.jar;D:\bea\weblogic81\server\lib\webservices.jar


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
java  -classpath C:\software\bea\weblogic81\server\lib\weblogic_sp.jar;C:\software\bea\weblogic81\server\lib\weblogic.jar;C:\software\bea\weblogic81\server\lib\webservices.jar; weblogic.Deployer  -user yangchen  -adminurl http://127.0.0.1:7001  -password yangchen  -activate  -name OSS -upload  -source D:\CVS\boss_p5.3_gh_cms.4\sourcecode\WebApp\OSS.ear -targets myserver
GOTO END
:UNDEPLOY
echo -----------------UNDEPLOY FILE------------
java  -classpath C:\software\bea\weblogic81\server\lib\weblogic_sp.jar;C:\software\bea\weblogic81\server\lib\weblogic.jar;C:\software\bea\weblogic81\server\lib\webservices.jar; weblogic.Deployer  -user yangchen  -adminurl http://127.0.0.1:7001  -password yangchen  -remove  -name OSS
GOTO END

:END
date /t
time /t
@endlocal
