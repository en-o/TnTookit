package io.tan.tookit.windows.util;

import cn.hutool.core.io.FileUtil;
import io.tan.tookit.util.CommandUtil;
import io.tan.tookit.util.TookitFileUtil;
import io.tan.tookit.windows.dto.InstallOpenRestyDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * @author tn
 * @version 1
 * @date 2021-11-23 22:51
 */
@Slf4j
public class NginxUtil {

    /**
     * 下载nginx
     * @param openRestyName 文件名
     * @param path 文件路径（判存）
     * @return filePath 下载的文件绝对路径
     */
    @SneakyThrows
    public static String nginxDownLoad(String openRestyName, String path) {
        // 验证下载存放路径是否存在
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            boolean mkdir = file.mkdirs();
            log.info("创建" + path + "文件夹:", mkdir);
        }
        // nginx存在就不用下了
        String filePath = path + File.separatorChar + openRestyName;
        if (!FileUtil.exist(filePath)) {
            if (CommandUtil.commandRun("powershell",
                    "$client = new-object System.Net.WebClient",
                    ";",
                    "$client.DownloadFile('https://openresty.org/download/" + openRestyName+"'," +
                            " '"+filePath.substring(1)+"')")) {
                log.info("下载" + openRestyName + "成功");
            }
        }else {
            log.info("文件" + openRestyName + "存在");
        }
        return filePath;
    }

    /**
     * 解压 nginx.zip
     * @param path  文件路径（判存）
     * @param installation installation
     * @param openRestyName 文件名
     * @param filePath 下载的文件绝对路径
     * @return unZipFilePath 解压路径
     */
    @SneakyThrows
    public static String nginxUnzip(String path, InstallOpenRestyDTO installation,
                                    String openRestyName,String filePath){
// 已经解压就不要在解压了
        // 解压 tools/windows/7-Zip
        String unzip = path.substring(1);
        if (StringUtils.isNotBlank(installation.getInstallPath())) {
            unzip = installation.getInstallPath();
        }
        String unZipFilePath = unzip + File.separatorChar  + openRestyName.substring(0, openRestyName.lastIndexOf("."));
        if (!FileUtil.exist(unZipFilePath)) {
            CommandUtil.commandRun(new File(ResourceUtils.getURL("classpath:").getPath()
                            + "tools/windows/7-Zip"),
                    "cmd",
                    "/c",
                    "7z",
                    "x",
                    "-o" + TookitFileUtil.getRealFilePath(unzip),
                    TookitFileUtil.getRealFilePath(filePath.substring(1)));
        }else {
            log.info("文件" + unZipFilePath + "已解压");
        }
        return unZipFilePath;
    }

    /**
     * 注册nginx
     * @param installation installation
     * @param unZipFilePath 解压路径
     */
    @SneakyThrows
    public static void register(InstallOpenRestyDTO installation, String unZipFilePath) {
        if(1 == installation.getRegisterService()){
            String reg = CommandUtil.commandExistForStr(
                    "cmd",
                    "/c",
                    "sc query nginx");
            if(null == reg || StringUtils.contains(reg,"服务名无效") ||
                    StringUtils.contains(reg,"指定的服务未安装") ){
                if(!FileUtil.exist(unZipFilePath +"\\mynginx.exe")){
                    CommandUtil.commandRun("cmd",
                            "/c",
                            "copy",
                            TookitFileUtil.getRealFilePath(ResourceUtils.getURL("classpath:").getPath().substring(1)
                                    +"tools/windows/winsw/WinSW-x64.exe"),
                            unZipFilePath +"\\mynginx.exe");
                }
                if(!FileUtil.exist(unZipFilePath +"\\mynginx.xml")) {
                    CommandUtil.commandRun("cmd",
                            "/c",
                            "copy",
                            TookitFileUtil.getRealFilePath(ResourceUtils.getURL("classpath:").getPath().substring(1)
                                    +"tools/windows/winsw/mynginx.xml"),
                            unZipFilePath +"\\mynginx.xml");
                }
                if(CommandUtil.commandRun(new File(unZipFilePath),
                        "cmd",
                        "/c",
                        "mynginx.exe uninstall  & mynginx.exe install")){
                    log.info("OpenResty 服务注册成功");
                    nginxStartAuto();
                }
            }else {
                log.info("OpenResty 服务已被注册");
            }

        }else{
            log.info("OpenResty 服务暂不注册");
        }
    }

    /**
     * 自启
     */
    public static void nginxStartAuto(){
        if(CommandUtil.commandRun("cmd",
                "/c",
                "sc config nginx start=auto & net start nginx")){
            log.info("OpenResty 自启成功");
        }
    }
}

