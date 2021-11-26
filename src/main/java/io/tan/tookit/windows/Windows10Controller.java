package io.tan.tookit.windows;

import com.detabes.annotation.mapping.PathRestController;
import com.detabes.result.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.tan.tookit.windows.maven.MavenUtil;
import io.tan.tookit.windows.maven.dto.InstallMavenDTO;
import io.tan.tookit.windows.maven.vo.MavenVO;
import io.tan.tookit.windows.mysql.dto.InstallMySqlDTO;
import io.tan.tookit.windows.mysql.vo.MySqlVO;
import io.tan.tookit.windows.nginx.NginxUtil;
import io.tan.tookit.windows.nginx.dto.InstallOpenRestyDTO;
import io.tan.tookit.windows.nginx.entity.NginxCommand;
import io.tan.tookit.windows.nginx.vo.InstallOpenRestyVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * win10环境部署
 *
 * @author tn
 * @version 1
 * @date 2021-11-23 10:15
 */
@Api(tags = {"winEnv"})
@PathRestController("win")
@Slf4j
public class Windows10Controller {

    /**
     * nginx
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "安装OpenResty", notes = "Nginx的下载，注册服务跟设置服务自启")
    @PostMapping("installOpenResty")
    public ResultVO<InstallOpenRestyVO> installOpenResty(@RequestBody @Valid InstallOpenRestyDTO installation) {
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
     * nginx
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "安装maven", notes = "下载跟设置全局环境变量")
    @PostMapping("installOMaven")
    public ResultVO<MavenVO> installOMaven(@RequestBody @Valid InstallMavenDTO mavenDTO) {
        String mavenName = mavenDTO.getFileName();
        // 下载文件
        String filePath = MavenUtil.mavenDownLoad(mavenName, mavenDTO.getMaven3Version());
        //  解压文件
        String unZipFilePath = MavenUtil.mavenUnzip(mavenDTO.getInstallPath(), mavenName, filePath);
        // 设置全局环境
        String message = MavenUtil.settingMvnEnv(unZipFilePath);
        String[] messageSplit = message.split("@@");
        MavenVO mavenVO = MavenVO.builder()
                .installPath(unZipFilePath)
                .settingPath(unZipFilePath+"\\conf")
                .remark(messageSplit[1])
                .build();
        return ResultVO.success(mavenVO,messageSplit[0]);
    }

    /**
     * mysql
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "安装MySQL", notes = "下载（解压版），安装，设置全局")
    @PostMapping("installOMaven")
    public ResultVO<MySqlVO> installOMySQL(@RequestBody @Valid InstallMySqlDTO mySqlDTO) {
        // TODO: 下载文件
        // TODO: 解压文件
        // TODO: 设置全局环境+设置默认配置
        return ResultVO.success(MySqlVO.builder().build(),"install ok");
    }



}
