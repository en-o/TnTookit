package io.tan.tookit.windows.nginx;

import cn.hutool.core.io.FileUtil;
import io.tan.tookit.constant.UrlConstant;
import io.tan.tookit.util.CommandUtil;
import io.tan.tookit.util.TookitFileUtil;
import io.tan.tookit.windows.nginx.dto.InstallOpenRestyDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.MessageFormat;

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
     * @return filePath 下载的文件绝对路径
     */
    @SneakyThrows
    public static String nginxDownLoad(String openRestyName) {
        // 验证下载存放路径是否存在
        File file = TookitFileUtil.getJarPathForFile();
        if (!file.exists() && !file.isDirectory()) {
            boolean mkdir = file.mkdirs();
            log.info("创建" + file.getParentFile() + "文件夹:", mkdir);
        }
        // nginx存在就不用下了
        String filePath = file.getParentFile().toString() + File.separatorChar + openRestyName;
        if (!FileUtil.exist(filePath)) {
            if (CommandUtil.commandRun("powershell",
                    "$client = new-object System.Net.WebClient",
                    ";",
                    "$client.DownloadFile('"+ MessageFormat.format(UrlConstant.NGINX_OPENRESTY_DOWNLOAD_URL,openRestyName) +"'," +
                            " '"+filePath+"')")) {
                log.info("下载" + openRestyName + "成功");
            }
        }else {
            log.info("文件" + openRestyName + "存在");
        }
        return filePath;
    }

    /**
     * 解压 nginx.zip
     * @param installation installation
     * @param openRestyName 文件名
     * @param filePath 下载的文件绝对路径
     * @return unZipFilePath 解压路径
     */
    @SneakyThrows
    public static String nginxUnzip(InstallOpenRestyDTO installation,
                                    String openRestyName,String filePath){
        // 已经解压就不要在解压了
        // 解压插件 tools/windows/7-Zip
        String unzip =  TookitFileUtil.getJarPathForFile().getParentFile().toString();
        String toolsPath = unzip+"\\tools\\windows\\7-Zip";
        if (StringUtils.isNotBlank(installation.getInstallPath())) {
            unzip = installation.getInstallPath();
        }
        String unZipFilePath = unzip + File.separatorChar  + openRestyName.substring(0, openRestyName.lastIndexOf("."));
        if (!FileUtil.exist(unZipFilePath)) {
            // 解压
            if(CommandUtil.commandRun(new File(toolsPath),
                    "cmd",
                    "/c",
                    "7z",
                    "x",
                    "-o" + TookitFileUtil.getRealFilePath(unzip),
                    TookitFileUtil.getRealFilePath(filePath))){
                log.info("文件" + unZipFilePath + "解压成功");
            }else {
                log.info("文件" + unZipFilePath + "解压失败");
            }
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

                String jarPath = TookitFileUtil.getJarPathForFile().getParentFile().toString();
                String toolsWinSWPath = jarPath+"\\tools\\windows\\winsw\\WinSW-x64.exe";
                String toolsNginxXMLWPath = jarPath+"\\tools\\windows\\winsw\\mynginx.xml";
                if(!FileUtil.exist(unZipFilePath +"\\mynginx.exe")){
                    CommandUtil.commandRun("cmd",
                            "/c",
                            "copy",
                            toolsWinSWPath,
                            unZipFilePath +"\\mynginx.exe");
                }
                if(!FileUtil.exist(unZipFilePath +"\\mynginx.xml")) {
                    CommandUtil.commandRun("cmd",
                            "/c",
                            "copy",
                            toolsNginxXMLWPath,
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

