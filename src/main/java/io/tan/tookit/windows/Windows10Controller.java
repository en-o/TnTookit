package io.tan.tookit.windows;

import com.detabes.annotation.mapping.PathRestController;
import com.detabes.result.result.ResultVO;
import io.swagger.annotations.ApiOperation;
import io.tan.tookit.util.TookitFileUtil;
import io.tan.tookit.windows.dto.InstallOpenRestyDTO;
import io.tan.tookit.windows.entity.NginxCommand;
import io.tan.tookit.windows.util.NginxUtil;
import io.tan.tookit.windows.vo.InstallOpenRestyVO;
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
@PathRestController("win")
@Slf4j
public class Windows10Controller {

    /**
     * nginx
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "安装OpenResty", notes = "Nginx")
    @PostMapping("installOpenResty")
    public ResultVO<InstallOpenRestyVO> installOpenResty(@RequestBody @Valid InstallOpenRestyDTO installation) {
        String openRestyName = installation.getFileName();
        String path = TookitFileUtil.getWindowsDefaultPath();
        // 下载文件
        String filePath = NginxUtil.nginxDownLoad(openRestyName, path);
        // 解压文件
        String unZipFilePath = NginxUtil.nginxUnzip(path, installation, openRestyName, filePath);
        // 注册自启
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
