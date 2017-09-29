#!/bin/sh

#要分割的文件
filename=$1

#分割后文件的前缀
sdpre=$2

#每个文件分割4W行
split -l 40000 ${filename} -d -a 3 ${sdpre}

#给分割后的文件加扩展名
ls|grep ${sdpre}|xargs -n1 -i{} mv {} {}.txt

echo "done"