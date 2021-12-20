#!/bin/env bash
# ENV
export PATH=/sbin:/bin:/usr/sbin:/usr/bin:/usr/local/sbin:/usr/local/bin

# MySQL configuration
# shellcheck disable=SC2034
# shellcheck disable=SC2116
mysql_version=$(echo "${1}")
# shellcheck disable=SC2034
# shellcheck disable=SC2116
mysql_password=$(echo "${2}")
# shellcheck disable=SC2034
# shellcheck disable=SC2116
mysql_port=$(echo "${3}")
# shellcheck disable=SC2034
# shellcheck disable=SC2116
mysql_install_dir=$(echo "${4}")
mysql_data_dir=${mysql_install_dir}"/data"

# shellcheck disable=SC2034
# shellcheck disable=SC2116
root_all_host=$(echo "${5}")
# shellcheck disable=SC2034
#download_mysql=$(echo ${10})
download_mysql="https://mirrors.tuna.tsinghua.edu.cn/mysql/downloads"


ENV_CHECK(){
  # shellcheck disable=SC1072
  # shellcheck disable=SC1020
  # shellcheck disable=SC1019
  # shellcheck disable=SC1073
  rpm -vih http://dl.fedoraproject.org/pub/epel/7/x86_64/e/epel-release-7-9.noarch.rpm
  yum -y install epel-release
  yum -y install wget tar jemalloc jemalloc-devel gcc gcc-c++

  if [ ! -d "${mysql_data_dir}" ];then
      echo "${mysql_data_dir}  not exist";
      mkdir -p  "${mysql_data_dir}"
  fi
}

Download_MySQL() {
  cd /mnt
  useradd -M -s /sbin/nologin mysql >/dev/null 2>&1
  wget -c ${download_mysql}/MySQL-8.0/mysql-"${mysql_version}"-linux-glibc2.12-x86_64.tar.xz --no-check-certificate
  echo "========== Start unzip ============"
  tar xJf mysql-${mysql_version}-linux-glibc2.12-x86_64.tar.xz
  \mv mysql-${mysql_version}-linux-glibc2.12-x86_64/* ${mysql_install_dir}
  if [[ -d "${mysql_install_dir}/support-files" ]]; then
    rm -rf mysql-${mysql_version}-linux-glibc2.12-x86_64
  else
    rm -rf ${mysql_install_dir}
    echo "Error: MySQL install failed, Please contact the author"
    kill -9 $$
  fi
}

Start_Initialize_MySQL() {
  echo "========== Start Initialize the database ============"
  ${mysql_install_dir}/bin/mysqld --initialize-insecure --user=mysql --basedir=${mysql_install_dir} --datadir=${mysql_data_dir}
  	chown -R root . ${mysql_install_dir}
  	chown -R mysql.mysql ${mysql_data_dir}
  	\cp -f ${mysql_install_dir}/support-files/mysql.server /etc/init.d/mysqld
  	sed -i "s@^basedir=.*@basedir=${mysql_install_dir}@" /etc/init.d/mysqld
  	sed -i "s@^datadir=.*@datadir=${mysql_data_dir}@" /etc/init.d/mysqld
  	sed -i "s@/usr/local/mysql@${mysql_install_dir}@g" ${mysql_install_dir}/bin/mysqld_safe
  	sed -i 's@executing mysqld_safe@executing mysqld_safe\nexport LD_PRELOAD=/usr/lib64/libjemalloc.so@' ${mysql_install_dir}/bin/mysqld_safe
  	echo 'PATH=$PATH:'${mysql_install_dir}'/bin' >/etc/profile.d/mariadb_renwole.com.sh

}

	# my.conf configuration
configuration_setting(){
cat > /etc/my.cnf << EOF
    [client]
    port = ${mysql_port}
    socket = /tmp/mysql.sock
    default-character-set = utf8mb4

    [mysql]
    prompt="MySQL [\\d]> "
    no-auto-rehash

    [mysqld]
    port = ${mysql_port}
    socket = /tmp/mysql.sock
    default_authentication_plugin = mysql_native_password

    basedir = ${mysql_install_dir}
    datadir = ${mysql_data_dir}
    pid-file = ${mysql_data_dir}/mysql.pid
    user = mysql
    bind-address = 0.0.0.0
    server-id = 1

    init-connect = 'SET NAMES utf8mb4'
    character-set-server = utf8mb4
    collation-server = utf8mb4_general_ci

    skip-name-resolve
    #skip-networking
    back_log = 300

    max_connections = 1000
    max_connect_errors = 6000
    open_files_limit = 65535
    table_open_cache = 128
    max_allowed_packet = 500M
    binlog_cache_size = 1M
    max_heap_table_size = 8M
    tmp_table_size = 16M

    read_buffer_size = 2M
    read_rnd_buffer_size = 8M
    sort_buffer_size = 8M
    join_buffer_size = 8M
    key_buffer_size = 4M

    thread_cache_size = 8

    ft_min_word_len = 4

    log_bin = mysql-bin
    binlog_format = mixed
    binlog_expire_logs_seconds = 604800

    log_error = ${mysql_data_dir}/mysql-error.log
    slow_query_log = 1
    long_query_time = 1
    slow_query_log_file = ${mysql_data_dir}/mysql-slow.log

    performance_schema = 0
    explicit_defaults_for_timestamp

    #lower_case_table_names = 1

    skip-external-locking

    default_storage_engine = InnoDB
    #default-storage-engine = MyISAM
    innodb_file_per_table = 1
    innodb_open_files = 500
    innodb_buffer_pool_size = 64M
    innodb_write_io_threads = 4
    innodb_read_io_threads = 4
    innodb_thread_concurrency = 0
    innodb_purge_threads = 1
    innodb_flush_log_at_trx_commit = 2
    innodb_log_buffer_size = 2M
    innodb_log_file_size = 32M
    innodb_log_files_in_group = 3
    innodb_max_dirty_pages_pct = 90
    innodb_lock_wait_timeout = 120

    bulk_insert_buffer_size = 8M
    myisam_sort_buffer_size = 8M
    myisam_max_sort_file_size = 10G
    myisam_repair_threads = 1

    interactive_timeout = 28800
    wait_timeout = 28800

    [mysqldump]
    quick
    max_allowed_packet = 500M

    [myisamchk]
    key_buffer_size = 8M
    sort_buffer_size = 8M
    read_buffer = 4M
    write_buffer = 4M
EOF
}

	# Set up MySQL
Set_up_MySQL() {
	chmod 600 /etc/my.cnf
	chmod +x /etc/init.d/mysqld
	systemctl enable mysqld
  	systemctl start mysqld
  	${mysql_install_dir}/bin/mysql -uroot -hlocalhost -e "create user root@'127.0.0.1' identified by \"${mysql_password}\";"
  	${mysql_install_dir}/bin/mysql -uroot -hlocalhost -e "grant all privileges on *.* to root@'127.0.0.1' with grant option;"
 	  ${mysql_install_dir}/bin/mysql -uroot -hlocalhost -e "grant all privileges on *.* to root@'localhost' with grant option;"
 	  if [ "$root_all_host" == "1" ];then
 	    	${mysql_install_dir}/bin/mysql -uroot -hlocalhost -e "create user root@'%' identified by \"${mysql_password}\";"
        ${mysql_install_dir}/bin/mysql -uroot -hlocalhost -e "grant all privileges on *.* to root@'%' with grant option;"
    fi
  	${mysql_install_dir}/bin/mysql -uroot -hlocalhost -e "alter user root@'localhost' identified by \"${mysql_password}\";"
  	${mysql_install_dir}/bin/mysql -uroot -p${mysql_password} -e "reset master;"
  	rm -rf /etc/ld.so.conf.d/{mysql,mariadb,percona,alisql}*.conf
  	echo "${mysql_install_dir}/lib" > /etc/ld.so.conf.d/mysql_renwole.com.conf
  	ldconfig
  	echo "========== MySQL installing Successfully ====="
  	echo
    echo "MySQL:"
    echo "account: root"
    echo "password: ${mysql_password}"
    echo "port: ${mysql_port}"
    echo "database: ${mysql_data_dir}"
    echo "=============================================="
}

Install_MySQL() {
  ENV_CHECK
  wait
  Download_MySQL
  wait
  Start_Initialize_MySQL
  wait
  configuration_setting
  wait
  Set_up_MySQL
}

## chmod 755 mysql_install.sh && ./mysql_install.sh 8.0.26 123456 3306 /tn/mysql
## chmod 755 mysql_install.sh && source mysql_install.sh 8.0.26 123456 3306 /tn/mysql
#  invoke Install_MySQL
Install_MySQL
source /etc/profile
