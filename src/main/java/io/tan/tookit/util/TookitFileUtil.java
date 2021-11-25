package io.tan.tookit.util;

import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * 文件相关util
 *
 * @author tn
 * @version 1
 * @date 2021-11-23 11:22
 */
public class TookitFileUtil {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    @SneakyThrows
    public static String getWindowsDefaultPath() {
        return ResourceUtils.getURL("classpath:").getPath() + "windows";
    }

    /**
     * 转换系统对应的的地址分隔符
     *
     * @param path path
     * @return String
     */
    public static String getRealFilePath(String path) {
        return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
    }

    /**
     * 获取 jar 所在目录
     *
     * @return String
     */
    public static String getJarPath() {
        ApplicationHome h = new ApplicationHome(TookitFileUtil.class);
        return h.getSource().getParentFile().toString();
    }

    /**
     * 获取 jar 所在目录
     *
     * @return String
     */
    public static File getJarPathForFile() {
        ApplicationHome h = new ApplicationHome(TookitFileUtil.class);
        return h.getSource();
    }

    /**
     * 判断文件夹是否存在
     * @param unzipPath 解压目录
     * @param unzipFileMainName 被解压文件的主目录
     * @return true 不存在
     */
    public static boolean existsDirectory(String unzipPath, String unzipFileMainName) {
        String unZipFilePath = unzipPath + File.separatorChar + unzipFileMainName;
        return !FileUtil.exist(unZipFilePath);
    }

}
