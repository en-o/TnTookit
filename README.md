<div style="text-align: center;">
	<a href="https://tannn.cn"  target="_blank">
	    <img src="https://tannn.cn/images/Img/TnTookit.jpg" width="400" alt="logo">
	</a>
</div>
<div style="text-align: center;">
	<strong>TnTookit 服务器/开发机 环境工具箱</strong>
</div>
<div style="text-align: center; margin-top: 20px">
    <a target="_blank" href="https://tannn.cn">
        <img src='https://img.shields.io/badge/license-GPL--3.0-brightgreen' alt='license'/>
    </a>
    <a target="_blank" href="https://tannn.cn">
        <img src='https://img.shields.io/badge/JDK-1.8.0_281+-green.svg' alt='jdk'/>
    </a>
    <a target="_blank" href="https://tannn.cn">
        <img src='https://gitee.com/etn/TnTookit/badge/star.svg?theme=dark' alt='gitee star'/>
    </a>
    <a target="_blank" href="https://tannn.cn">
        <img src='https://img.shields.io/badge/Author-谭宁-orange.svg' alt='author'/>
    </a>
    <a target="_blank" href="https://tannn.cn">
        <img src='https://img.shields.io/badge/version-v0.0.1beta-brightgreen.svg' alt='version'/>
    </a>
</div>


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
2. maven3.x的下载和配置全局环境
   1. maven下载是用的apache的下载地址，有时候很慢慢需要等很久
   2. 如果嫌等太久，目前可以本地把maven文件下好，放到jar包同目录下
3. mysql8.x 版本安装跟注册服务   
   1. 手动检查电脑是否有VC_redist.x64.exe环境（建议直接带点击安装之后在调用接口）
   2. 由于目前无法检测到VC_redist.x64.exe的安装所以必须手动检测
   3. 要安装多个数据库，请选择不通的安装路径跟端口和服务名
## win公共功能
1. 根据端口查询进程名
2. 根据端口杀进程

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