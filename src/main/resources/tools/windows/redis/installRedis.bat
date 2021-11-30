@echo off
chcp 936
set servicename=%1
set ports=%2
redis-server.exe --service-uninstall --service-name %servicename%  & redis-server.exe --service-install redis.windows.conf  --service-name %servicename% --loglevel verbose --port %ports%  & redis-server.exe --service-start --service-name %servicename%
::& exit