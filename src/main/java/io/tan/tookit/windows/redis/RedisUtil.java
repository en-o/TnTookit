package io.tan.tookit.windows.redis;

import cn.hutool.core.io.FileUtil;
import io.tan.tookit.constant.UrlConstant;
import io.tan.tookit.util.CommandUtil;
import io.tan.tookit.util.TookitFileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.MessageFormat;

/**
 * 工具
 *
 * @author tn
 * @date 2021-11-29 16:14
 */
@Slf4j
public class RedisUtil {


    /**
     * redis tporadowski 下载
     *
     * @param redisName 文件名
     * @return filePath 下载的文件路径
     */
    public static String redisDownLoad(String redisName, String version) {
        // maven存在就不用下了
        String filePath = TookitFileUtil.getJarPathForFile().getParentFile().toString() + File.separatorChar + redisName;
        if (!FileUtil.exist(filePath)) {
            if (CommandUtil.commandRun("powershell",
                    "$client = new-object System.Net.WebClient;",
                    "$client.Headers.Add('user-agent', 'Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)');",
                    "$client.DownloadFile('" + MessageFormat.format(UrlConstant.REDIS_WIN_DOWNLOAD_URL, version, redisName) + "'," +
                            " '" + filePath + "');")) {
                log.info("下载" + redisName + "成功");
            }
        } else {
            log.info("文件" + redisName + "存在");
        }
        return filePath;

    }


    /**
     * 解压 redis.zip
     *
     * @param installPath 解压路径
     * @param redisName   文件名
     * @param filePath    下载的文件绝对路径
     * @return unZipFilePath 解压路径
     */
    public static String redisUnzip(String installPath,
                                    String redisName, String filePath) {

        String fileNameSub = redisName.substring(0, redisName.lastIndexOf("."));
        installPath = installPath + File.separatorChar + fileNameSub;
        return TookitFileUtil.winUnZip(installPath, redisName, filePath, null);
    }


    /**
     *  安装redis
     *
     * @param port 设置端口
     * @param password 设置密码
     * @param serviceName 服务名（安装多个时用）
     * @param unZipFilePath musql解压路径
     * @return message
     */
    public static String addService(String port,
                                         String password,
                                         String serviceName,
                                         String unZipFilePath) {
        // 工具文件夹
        String tools = TookitFileUtil.getJarPathForFile().getParentFile().toString();
        String installBat = tools + "\\tools\\windows\\redis\\installRedis.bat";
        // 在redis根路径添加脚本
        boolean b = CommandUtil.commandRun("cmd",
                "/c",
                "copy",
                installBat,
                unZipFilePath + "\\installRedis.bat");
        // 安装
        return CommandUtil.commandRunStr("cmd",
                "/c",
                unZipFilePath + "\\installRedis.bat",serviceName,
                unZipFilePath,
                port);
    }
}
