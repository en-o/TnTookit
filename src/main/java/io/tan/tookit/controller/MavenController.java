package io.tan.tookit.controller;

import cn.jdevelops.annotation.mapping.PathRestController;
import cn.jdevelops.result.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.tan.tookit.windows.maven.MavenUtil;
import io.tan.tookit.windows.maven.dto.InstallMavenDTO;
import io.tan.tookit.windows.maven.entity.MavenCommand;
import io.tan.tookit.windows.maven.vo.MavenVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 环境部署
 *
 * @author tn
 * @version 1
 * @date 2021-11-23 10:15
 */
@Api(tags = {"maven"})
@PathRestController("maven")
@Slf4j
public class MavenController {


    /**
     * WIN10 maven
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "win10安装maven", notes = "下载跟设置全局环境变量")
    @PostMapping("/win/installMaven")
    public ResultVO<MavenVO> installMavenByWin(@RequestBody @Valid InstallMavenDTO mavenDTO) {
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
                .settingPath(unZipFilePath + "\\conf")
                .remark(messageSplit[1])
                .build();
        mavenVO.setCommand(MavenCommand.builder().build());
        return ResultVO.success(mavenVO, messageSplit[0]);
    }



}
