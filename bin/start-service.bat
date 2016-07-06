setlocal
call "%~dp0setting.bat"

SET DUBBO_PROPERTIES=dubbo-mysql.properties
echo on
java "-Dmonitor.log.home=%LOG_HOME%" -cp "%CLASSPATH%" "%MAINCLASS%"
endlocal