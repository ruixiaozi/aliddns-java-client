### Aliddns-java-client 阿里动态域名解析客户端
---
开发环境：IntellJ IDEA 2019.2 + Jdk 1.8 + maven
版本：1.0.1
作者：ruixiaozi
Github地址：https://github.com/ruixiaozi/aliddns-java-client
博客：https://www.ruixiaozi.com
邮箱：admin@ruixiaozi.com

1. 功能介绍  
+ 实现阿里云DNS的动态解析
+ 支持IPv4/IPv6
+ 一键安装包只支持Centos 7+/Ubuntu 16+ （其他发行版未测试）
+ 一键安装包实现了Linux守护进程

2. 安装包使用说明  

+ 下载地址：[https://github.com/ruixiaozi/aliddns-java-client/releases](https://github.com/ruixiaozi/aliddns-java-client/releases)

+ 步奏一：  

更改aliddns.conf文件
```
{
    "accessKeyId":"你的accessKeyId",
    "accessKeySecret ":""你的accessKeySecret ",
    "domainName":"你的域名",
    "host":"主机名",
    "isIpv6":true		##true表示用ipv6，false表示用ipv4
}
```

+ 步奏二：  

运行install.sh即可安装成功，并已经开始运行

+ 命令说明：  

查看状态：sudo systemctl status aliddns

启动|停止|重启：sudo systemctl start|stop|restart aliddns