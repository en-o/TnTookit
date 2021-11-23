package io.tan.tookit.windows;

import cn.hutool.core.io.FileUtil;
import com.detabes.annotation.mapping.PathRestController;
import com.detabes.result.result.ResultVO;
import io.swagger.annotations.ApiOperation;
import io.tan.tookit.util.CommandUtil;
import io.tan.tookit.util.TookitFileUtil;
import io.tan.tookit.windows.constant.CMDConstant;
import io.tan.tookit.windows.dto.InstallOpenRestyDTO;
import io.tan.tookit.windows.entity.NginxCommand;
import io.tan.tookit.windows.vo.InstallOpenRestyVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;

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
    @GetMapping("installOpenResty")
    public ResultVO<InstallOpenRestyVO> installOpenResty(InstallOpenRestyDTO installation) {
        String openRestyName = installation.getFileName();
        String path = TookitFileUtil.getWindowsDefaultPath();
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            boolean mkdir = file.mkdirs();
            log.info("创建" + path + "文件夹:", mkdir);
        }
        Runtime runtime = Runtime.getRuntime();
        // nginx存在就不用下了
        String filePath = path + File.separatorChar + openRestyName;
        if (!FileUtil.exist(filePath)) {
            Process exec = runtime.exec(
                    CMDConstant.DOWNLOAD_CERTUTIL + " https://openresty.org/download/" + openRestyName,
                    null,
                    file);
            // 会阻塞等待进程执行完
            int i = exec.waitFor();
            if (i == 0) {
                log.info("下载" + openRestyName + "成功");
            }
        }else {
            log.info("文件" + openRestyName + "存在");
        }
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
                    "-o" + unzip,
                    filePath.substring(1));
        }else {
            log.info("文件" + unZipFilePath + "已解压");
        }
        InstallOpenRestyVO restyVO = InstallOpenRestyVO.builder()
                .configPath(unZipFilePath + "/conf")
                .installPath(unzip)
                .build();
        restyVO.setNginxCommand(NginxCommand.builder()
                .build());
        return ResultVO.success(restyVO, "install ok");

    }


}
