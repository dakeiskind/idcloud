SET CONFDIR=%~dp0%..\conf
SET LOG_HOME=%~dp0%..\logs
SET CLASSPATH=%~dp0..\*;%CLASSPATH%;%CONFDIR%
set MAINCLASS=com.alibaba.dubbo.container.Main