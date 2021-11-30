package io.tan.tookit.windows;

import com.detabes.annotation.mapping.PathRestController;
import com.detabes.result.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"winEnv - nginx"})
@PathRestController("win/nginx")
@Slf4j
public class NginxController {

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


}