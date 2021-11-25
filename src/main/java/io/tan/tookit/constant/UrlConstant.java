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

}
