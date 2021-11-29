chcp 65001
@echo off
set servicename=%1
set filepath=%2
set ports=%3
cd /d %filepath%
echo install redis-server ==> dir：%cd%
%cd%\redis-server.exe --service-uninstall --service-name %servicename%  & %cd%\redis-server.exe --service-install --port %ports% %cd%\redis.windows.conf  --service-name %servicename% --loglevel verbose  & %cd%\redis-server.exe --service-start --service-name %servicename%
::& exit