# TnTookit
> 我的工具箱

# 注意事项
## 项目依赖resources目录下的tools包
1. 源码启动请把tools（包含tools）整个包移动到 target 下使用
2. jar启动请把tools（包含tools）整个包跟随jar放在一起tree -
```text
C:.
└─tools
├    └─windows
├        ├─7-Zip
├        │  └─Lang
├        └─winsw
└─tanTookit-0.0.1-SNAPSHOT.jar
```


# 完成功能
## windows 10
> 源码测试IDEA必须用管理员模式启动    
> jar 使用也必须在管理员CMD中运行   
1. OpenResty(nginx)的安装注册服务加自启
2. maven的下载和配置全局环境
   1. maven下载是用的apache的下载地址，有时候很慢慢需要等很久
   2. 如果嫌等太久，目前可以本地把maven文件下好，放到jar包同目录下

# 用途
1. 安装开发中用到的环境

# 服务器环境
1. windows 
- windows 10+
- server 2012
- server 2008
2. linux
- centos 7+

# 开发语言
- java
- vue