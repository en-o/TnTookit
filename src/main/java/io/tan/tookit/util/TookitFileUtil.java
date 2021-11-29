package io.tan.tookit.util;

import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
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
        String unZipFilePath = unzipPath;
        if(StringUtils.isNotBlank(unzipFileMainName)){
            unZipFilePath = unzipPath + File.separatorChar + unzipFileMainName;
        }
        return !FileUtil.exist(unZipFilePath);
    }


    /**
     *
     * @param installPath 解压路径
     * @param fileName   文件名
     * @param filePath    下载的文件绝对路径
     * @param unZipName  解压后的总目录名
     * @return unZipFilePath 解压路径
     */
    public static String winUnZip(String installPath, String fileName, String filePath,String unZipName) {
        // 已经解压就不要在解压了
        String unzip = TookitFileUtil.getJarPathForFile().getParentFile().toString();
        if (StringUtils.isNotBlank(installPath)) {
            unzip = installPath;
        }
        if (TookitFileUtil.existsDirectory(unzip, unZipName)) {
            CommandUtil.unzip7Z(installPath, fileName, filePath);
        } else {
            log.info("文件" + fileName + "早已解压");
        }
        if(StringUtils.isNotBlank(unZipName)){
            return getRealFilePath(unzip + TookitFileUtil.FILE_SEPARATOR + unZipName);
        }else {
            return getRealFilePath(unzip);
        }

    }

}
