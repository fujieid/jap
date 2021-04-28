#!/bin/bash

#-----------------------------------------------------------
# 参考自 hutool 工具
# 此脚本用于每次升级 jap 时替换相应位置的版本号
#-----------------------------------------------------------
pwd=$(pwd)
echo "当前路径：$pwd"

if [ -n "$1" ];then
    new_version="$1"
    old_version=`cat $pwd/docs/bin/version.txt`
    echo "将项目版本 v$old_version 修改为 v$new_version"
else
    # 参数错误，退出
    echo "ERROR: 请指定新版本！"
    exit
fi

if [ ! -n "$old_version" ]; then
    echo "ERROR: 旧版本不存在，请确认 docs/bin/version.txt 中信息正确"
    exit
fi

echo "替换 $pwd/README.md"
sed -i "s/${old_version}/${new_version}/g" $pwd/README.md
echo "替换 $pwd/README.en.md"
sed -i "s/${old_version}/${new_version}/g" $pwd/README.en.md
echo "替换 $pwd/jap-bom/pom.xml"
sed -i "s/${old_version}/${new_version}/g" $pwd/jap-bom/pom.xml
echo "替换 $pwd/pom.xml"
sed -i "s/${old_version}/${new_version}/g" $pwd/pom.xml

# 保留新版本号
echo "$new_version" > $pwd/docs/bin/version.txt
