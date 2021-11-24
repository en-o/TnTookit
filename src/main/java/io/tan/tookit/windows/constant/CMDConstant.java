package io.tan.tookit.windows.constant;

/**
 * cmd命令
 *
 * @author tn
 * @version 1
 * @date 2021-11-23 12:55
 */
public class CMDConstant {

    /**
     * 下载文件
     * DOWNLOAD_CERTUTIL + url
     * 使用时会被杀毒软件杀
     */
    public static final String DOWNLOAD_CERTUTIL = "certutil & certutil –urlcache –f –split ";

    /**
     *  一定要安装了7z，且设置了全局环境
     * 7z 解压
     * <>7z x file.zip</>
     * @link http://cnblogs.com/conorblog/p/14543286.html
     */
    public static final String Z7_UNZIP = "7z x ";

    /**
     * 一定要安装了7z，且设置了全局环境
     * 7z 解压 指定解压目标存放目录
     * <>7z x  -otest file.zip</>
     * <>7z x  -of:\tn\123 file.zip</>
     */
    public static final String Z7_UNZIP_DIRECTORY = "7z x -o";



}
