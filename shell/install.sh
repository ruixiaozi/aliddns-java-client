#!/bin/bash
appName="aliddns-java-client"
shortAppName="aliddns"
appVersion="1.0.1"
echo "开始安装 "${appName}"-"${appVersion}
sudo mkdir /etc/${shortAppName}
sudo cp -rf ./${shortAppName}.conf /etc/${shortAppName}/${shortAppName}.conf
sudo mkdir /usr/local/${shortAppName}
javaHome=$(which java)
sudo ln -sf ${javaHome} /usr/local/${shortAppName}/java
sudo cp -rf ./${shortAppName}.jar /usr/local/${shortAppName}/${shortAppName}.jar
sudo cp -rf ./${shortAppName}.service /etc/systemd/system/${shortAppName}.service
sudo systemctl daemon-reload
sudo systemctl stop ${shortAppName}.service
sudo systemctl enable ${shortAppName}.service
sudo systemctl restart ${shortAppName}.service
echo "安装成功！"${appName}"-"${appVersion}