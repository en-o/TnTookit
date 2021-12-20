package io.tan.tookit.constant;

/**
 * 地址常量
 *
 * @author tn
 * @date 2021-11-25 09:16
 */
public class UrlConstant {

    /**
     * openresty nginx下载地址
     * 《MessageFormat.format(MAVEN_DOWNLOAD_URL,fileName)》
     */
    public static final  String NGINX_OPENRESTY_DOWNLOAD_URL = "https://openresty.org/download/{0}";

    /**
     * 根据版本下载
     * maven3.x 下载地址 ： MAVEN_DOWNLOAD_URL+/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.zip
     * 根据版本下载 // 《MessageFormat.format(MAVEN_DOWNLOAD_URL,3.6.3,fileName)》
     */
    public static final String MAVEN3_DOWNLOAD_URL = "https://dlcdn.apache.org/maven/maven-3/{0}/binaries/{1}";

    /**
     * 根据版本下载
     * mysql-8.0.25-winx64.zip
     * mysql下载地址：https://mirrors.huaweicloud.com/mysql/Downloads/MySQL-8.0/
     * 《MessageFormat.format(MYSQL8_DOWNLOAD_URL,fileName)》
     */
    public static final String MYSQL8_DOWNLOAD_URL = "https://mirrors.huaweicloud.com/mysql/Downloads/MySQL-8.0/{0}";

    /**
     * mysql linux  清华源
     */
    public static final String LINUX_MYSQL8_DOWNLOAD_URL = "https://mirrors.tuna.tsinghua.edu.cn/mysql/downloads/MySQL-8.0/{0}";

    /**
     * window redis下载
     * Redis-x64-4.0.2.2.zip
     * window redis下载：https://github.com/tporadowski/redis/releases
     * 代理地址 ;https://github.rc1844.workers.dev/
     *
     * 《MessageFormat.format(REDIS_WIN_DOWNLOAD_URL,4.0.2.2,fileName)》
     */
    public static final String REDIS_WIN_DOWNLOAD_URL = "https://github.rc1844.workers.dev/tporadowski/redis/releases/download/v{0}/{1}";

}
