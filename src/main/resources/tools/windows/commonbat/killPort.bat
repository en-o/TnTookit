chcp 936
@echo off
rem 需要关闭启动的端口
set port=%1
for /f "usebackq tokens=1-5" %%a in (`netstat -ano ^| findstr %port%`) do (
  if [%%d] EQU [LISTENING] (
    set pid=%%e
  )
)

for /f "usebackq tokens=1-5" %%a in (`tasklist ^| findstr %pid%`) do (
  set image_name=%%a
)

echo now will kill process : pid %pid%, image_name %image_name%
rem 根据进程ID，kill进程
taskkill /f /pid %pid%
@echo %image_name% stop success