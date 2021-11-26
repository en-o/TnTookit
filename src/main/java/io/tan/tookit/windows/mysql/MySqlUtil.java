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


}
