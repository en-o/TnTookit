package io.tan.tookit.windows.mysql;

import cn.hutool.core.io.FileUtil;
import io.tan.tookit.constant.UrlConstant;
import io.tan.tookit.util.CommandUtil;
import io.tan.tookit.util.TookitFileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.MessageFormat;

/**
 * msql
 *
 * @author tn
 * @date 2021-11-26 11:08
 */
@Slf4j
public class MySqlUtil {

    /**
     * @param mysqlName 文件名
     * @return filePath 下载的文件路径
     */
    public static String mysqlDownLoad(String mysqlName) {
        File file = TookitFileUtil.getJarPathForFile();
        // maven存在就不用下了
        String filePath = file.getParentFile().toString() + File.separatorChar + mysqlName;
        if (!FileUtil.exist(filePath)) {
            if (CommandUtil.commandRun("powershell",
                    "$client = new-object System.Net.WebClient",
                    ";",
                    "$client.DownloadFile('" + MessageFormat.format(UrlConstant.MYSQL8_DOWNLOAD_URL, mysqlName) + "'," +
                            " '" + filePath + "')")) {
                log.info("下载" + mysqlName + "成功");
            }
        } else {
            log.info("文件" + mysqlName + "存在");
        }
        return filePath;

    }


    /**
     * 解压 mysql.zip
     *
     * @param installPath 解压路径
     * @param mysqlName   文件名
     * @param filePath    下载的文件绝对路径
     * @return unZipFilePath 解压路径
     */
    public static String mysqlUnzip(String installPath,
                                    String mysqlName, String filePath) {

        String fileNameSub = mysqlName.substring(0, mysqlName.lastIndexOf("."));
        return TookitFileUtil.winUnZip(installPath, mysqlName, filePath, fileNameSub);
    }


    /**
     * 设置 安装mysql
     * @param port 设置端口
     * @param password 设置密码
     * @param serviceName 服务名（安装多个时用）
     * @param unZipFilePath musql解压路径
     * @return message
     */
    public static String settingMysqlEnv(String port,
                                         String password,
                                         String serviceName,
                                         String unZipFilePath) {
        // 工具文件夹
        String tools = TookitFileUtil.getJarPathForFile().getParentFile().toString();
        String vcRuntime = tools + "\\tools\\windows\\mysql\\VC_redist.x64.exe";
        String installBat = tools + "\\tools\\windows\\mysql\\mysql8\\mysql_install.bat";

        // 在mysql根路径添加脚本
        boolean b = CommandUtil.commandRun("cmd",
                "/c",
                "copy",
                installBat,
                unZipFilePath + "\\mysql_install.bat");
        if(b){
            // 安装
            String str = CommandUtil.commandRunStr("cmd",
                    "/c",
                    unZipFilePath + "\\mysql_install.bat", port,
                    password,
                    serviceName);
            return str;
        }else {
            return "找不到安装脚本";
        }

    }


}
