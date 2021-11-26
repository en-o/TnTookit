chcp 936
@setlocal enabledelayedexpansion
@echo off

set basedir=%~dp0
set bin=%~dp0\bin
set port=%1
set password=%2
set servername=%3
cd /d %~dp0
if exist !bin!\mysqld.exe (
>my.ini echo [mysqld]
>>my.ini echo port=%port%
>>my.ini echo basedir=%basedir:\=\\%
>>my.ini echo datadir=%basedir:\=\\%data
>>my.ini echo log-error=%basedir:\=\\%logs.logs
>>my.ini echo max_connections=200
>>my.ini echo max_connect_errors=10
>>my.ini echo character-set-server=utf8mb4
>>my.ini echo default-storage-engine=INNODB
>>my.ini echo default_authentication_plugin=mysql_native_password
>>my.ini echo local_infile=ON
>>my.ini echo sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION
>>my.ini echo log-bin=mysql-bin
>>my.ini echo binlog-format=Row
>>my.ini echo.
>>my.ini echo [mysql]
>>my.ini echo default-character-set=utf8mb4
>>my.ini echo local_infile=ON
>>my.ini echo.
>>my.ini echo [client]
>>my.ini echo port=%port%
>>my.ini echo default-character-set=utf8mb4

echo start check env...
echo.
for /f "tokens=2*" %%i ^
in ('reg query "HKLM\SYSTEM\ControlSet001\Control\Session Manager\Environment" /v Path^|findstr /i "path"') ^
do (set "p=%%j")
for /f "tokens=*" %%a in ('echo !p! ^| find /i "!bin!"') do (set flag=1)
if not defined flag (
echo add env
setx Path "!p!;!bin!" /M | find "成功" && echo add env success
set p=!Path!
) else (echo env exist,Don't setting)
echo.


cd !bin!

>!basedir!\logs.log echo --------start initialize scheme--------

echo start initialize scheme

mysqld --initialize-insecure

>>!basedir!\logs.log echo --------end initialize scheme--------

echo start install service
mysqld --install %servername% | find "successfully" && echo  service install success
echo.

echo startUp  service
net start %servername%
echo.

mysql -u root -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '%password%'";

echo install success
) else (
echo Please open this tool in the MySQL root directory after decompression
)
exit