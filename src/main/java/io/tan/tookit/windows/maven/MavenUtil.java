package io.tan.tookit.windows.maven;

import cn.hutool.core.io.FileUtil;
import io.tan.tookit.constant.UrlConstant;
import io.tan.tookit.util.CommandUtil;
import io.tan.tookit.util.TookitFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.MessageFormat;

/**
 * maven
 *
 * @author tn
 * @date 2021-11-24 16:58
 */
@Slf4j
public class MavenUtil {

    /**
     * 下载 maven
     *
     * @param mavenName 文件名
     * @param version   版本号
     * @return 下载成功后的文件路径
     */
    public static String mavenDownLoad(String mavenName, String version) {
        File file = TookitFileUtil.getJarPathForFile();
        // maven存在就不用下了
        String filePath = file.getParentFile().toString() + File.separatorChar + mavenName;
        if (!FileUtil.exist(filePath)) {
            if (CommandUtil.commandRun("powershell",
                    "$client = new-object System.Net.WebClient",
                    ";",
                    "$client.DownloadFile('" + MessageFormat.format(UrlConstant.MAVEN3_DOWNLOAD_URL, version, mavenName) + "'," +
                            " '" + filePath + "')")) {
                log.info("下载" + mavenName + "成功");
            }
        } else {
            log.info("文件" + mavenName + "存在");
        }
        return filePath;

    }


    /**
     * 解压 maven.zip
     *
     * @param installPath 解压路径
     * @param mavenName   文件名
     * @param filePath    下载的文件绝对路径
     * @return unZipFilePath 解压路径
     */
    public static String mavenUnzip(String installPath,
                                    String mavenName, String filePath) {
        String mavenNameSub = mavenName.substring(0, mavenName.lastIndexOf("-"));
        // 已经解压就不要在解压了
        String unzip = TookitFileUtil.getJarPathForFile().getParentFile().toString();
        if (StringUtils.isNotBlank(installPath)) {
            unzip = installPath;
        }
        if (TookitFileUtil.existsDirectory(unzip, mavenNameSub)) {
            CommandUtil.unzip7Z(installPath, mavenName, filePath);
        } else {
            log.info("文件" + mavenName + "早已解压");
        }
        return TookitFileUtil.getRealFilePath(unzip + TookitFileUtil.FILE_SEPARATOR + mavenNameSub);
    }

    /**
     * 设置mavne全局环境
     *
     * @param filePath maven根路径
     * @return message
     */
    public static String settingMvnEnv(String filePath) {
        String checkEnvironment = CommandUtil.commandExistForStr("powershell", "mvn", "-v");
        if (StringUtils.contains(checkEnvironment, "Apache Maven")) {
            return "maven已有全局环境无需配置@@_" + checkEnvironment;
        } else {
            CommandUtil.commandRun("cmd",
                    "setx /M \"MAVEN_HOME\" \"" + TookitFileUtil.getRealFilePath(filePath) + "\"",
                    ";");
            CommandUtil.commandRun("cmd",
                    "/c",
                    "wmic ENVIRONMENT where \"name='PATH' and username='<system>'\" set VariableValue=\"%PATH%;%MAVEN_HOME%\\bin\"");
            checkEnvironment = CommandUtil.commandExistForStr("cmd", "set", "MAVEN_HOME");
            if (StringUtils.contains(checkEnvironment, "MAVEN_HOME=")) {
                return "maven全局环境配置成功@@_" + checkEnvironment;
            } else {
                return "maven全局环境配置失败@@_" + checkEnvironment;
            }

        }
    }
}
