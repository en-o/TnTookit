chcp 936
@setlocal enabledelayedexpansion
@echo off

SET dbhost=%1
SET dbuser=%2
SET dbpasswd=%3
set sqlfile=%4

# 如果没有设置 mysql 的全局变量就要加上这个
# cd /d C:\Program Files\MySQL\MySQL Server 8.0\bin

::执行SQL脚本

mysql -h%dbhost% -u%dbuser% -p%dbpasswd% < %sqlfile% --default-character-set=utf8

ECHO OK!
PAUSE

@ECHO Done!