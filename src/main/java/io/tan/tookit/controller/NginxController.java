package io.tan.tookit.controller;

import cn.jdevelops.annotation.mapping.PathRestController;
import cn.jdevelops.result.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.tan.tookit.util.CommandUtil;
import io.tan.tookit.util.TookitFileUtil;
import io.tan.tookit.windows.nginx.NginxUtil;
import io.tan.tookit.windows.nginx.dto.InstallOpenRestyDTO;
import io.tan.tookit.windows.nginx.entity.NginxCommand;
import io.tan.tookit.windows.nginx.vo.InstallOpenRestyVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

/**
 * 环境部署
 *
 * @author tn
 * @version 1
 * @date 2021-11-23 10:15
 */
@Api(tags = {"nginx"})
@PathRestController("nginx")
@Slf4j
public class NginxController {

    /**
     * win10 nginx
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "win10安装OpenResty", notes = "Nginx的下载，注册服务跟设置服务自启")
    @PostMapping("/win/installOpenResty")
    public ResultVO<InstallOpenRestyVO> installOpenRestyByWin(@RequestBody @Valid InstallOpenRestyDTO installation) {
        String openRestyName = installation.getFileName();
        // 下载文件
        String filePath = NginxUtil.nginxDownLoad(openRestyName);
        // 解压文件
        String unZipFilePath = NginxUtil.nginxUnzip(installation, openRestyName, filePath);
//        // 注册自启
        NginxUtil.register(installation, unZipFilePath);
        InstallOpenRestyVO restyVO = InstallOpenRestyVO.builder()
                .configPath(unZipFilePath + "\\conf")
                .installPath(unZipFilePath)
                .build();
        restyVO.setNginxCommand(NginxCommand.builder()
                .build());
        return ResultVO.success(restyVO, "install ok");
    }


    /**
     * centos nginx
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "centos安装OpenResty", notes = "Nginx的下载，注册服务跟设置服务自启(最高版本)")
    @PostMapping("/centos/installOpenResty")
    public ResultVO<String> installOpenRestyByCentos() {
        // jar文件夹
        String jarPath = TookitFileUtil.getJarPathForFile().getParentFile().toString();
        //  脚本文件夹
        String installShell = jarPath + "/tools/linux/nginx/nginx_install.sh";
        String msg = CommandUtil.commandRunCharset(StandardCharsets.UTF_8,"bash", "-c", "chmod 755 " + installShell + " && source " + installShell);
        return ResultVO.success(msg,"install ok");
    }

}
