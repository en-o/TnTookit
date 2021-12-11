#!/bin/bash


if ! type vim >/dev/null 2>&1; then
	yum install vim -y
    echo 'failed'
else
    echo 'vim exist'
fi

wait


v_pcre_devel=$(rpm -qa pcre)
if [ ${#v_pcre_devel} -eq 0 ]
    then
        yum install pcre-devel -y
		echo 'pcre-devel install succeed'
        exit 0
    else
        echo 'pcre-devel exist'
fi


wait

v_openssl_devel=$(rpm -qa openssl)
if [ ${#v_openssl_devel} -eq 0 ]
    then
		yum install openssl-devel -y
		echo 'openssl-develinstall succeed'
        exit 0
    else
        echo 'openssl-devel exist '
fi



if ! type gcc >/dev/null 2>&1; then
	yum install gcc -y
    echo 'gcc install succeed'
else
    echo 'gcc exist'
fi

wait

if ! type curl >/dev/null 2>&1; then
	yum install curl -y
    echo 'curl install succeed'
else
    echo 'curl exist'
fi

wait

if ! type wget >/dev/null 2>&1; then
	yum install wget -y
    echo 'wget install succeed'
else
    echo 'wget exist'
fi

wait


if [ -f "/etc/yum.repos.d/openresty.repo" ];then
  echo "openresty.repo exist"
else
	echo "openresty.repo not exist";
	wget https://openresty.org/package/centos/openresty.repo
	wait
	sudo mv openresty.repo /etc/yum.repos.d/
	wait
	# update the yum index:
	sudo yum check-update
fi


wait

if ! type openresty >/dev/null 2>&1; then
	sudo yum install -y openresty
    echo 'openresty install succeed'
else
    echo 'openresty exist'
fi

wait


env=$PATH
nginxenv="openresty"
if [[ $env == *$nginxenv* ]]
then
    echo   "openresty  env exist " 
else
	echo "export PATH=$PATH:/usr/local/openresty/nginx/sbin">>/etc/profile
	echo   "openresty env install succeed  " 
fi

wait
source /etc/profile

wait

if [ -f "/usr/lib/systemd/system/nginx.service" ];then
  echo "nginx.service"
else
	nginxservice=/usr/lib/systemd/system/nginx.service 
	touch $nginxservice
	echo [Unit] >> $nginxservice  >> $nginxservice
	echo Description=The nginx HTTP and reverse proxy server  >> $nginxservice
	echo After=network.target remote-fs.target nss-lookup.target  >> $nginxservice
	echo [Service]  >> $nginxservice
	echo Type=forking  >> $nginxservice
	echo PIDFile= /var/run/openresty.pid  >> $nginxservice
	echo ExecStartPre=/usr/bin/rm -f  /var/run/openresty.pid  >> $nginxservice
	echo ExecStartPre=/usr/local/openresty/nginx/sbin/nginx -t  >> $nginxservice
	echo ExecStart=/usr/local/openresty/nginx/sbin/nginx  >> $nginxservice
	echo ExecReload=/bin/kill -s HUP $MAINPID  >> $nginxservice
	echo KillMode=process  >> $nginxservice
	echo KillSignal=SIGQUIT  >> $nginxservice
	echo TimeoutStopSec=5  >> $nginxservice
	echo PrivateTmp=true  >> $nginxservice
	echo [Install]  >> $nginxservice
	echo WantedBy=multi-user.target  >> $nginxservice
fi
wait
systemctl daemon-reload
wait
systemctl enable nginx.service