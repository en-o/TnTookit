package io.tan.tookit.windows;

import com.detabes.annotation.mapping.PathRestController;
import com.detabes.result.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.tan.tookit.windows.mysql.MySqlUtil;
import io.tan.tookit.windows.mysql.dto.InstallMySqlDTO;
import io.tan.tookit.windows.mysql.entity.MySqlCommand;
import io.tan.tookit.windows.mysql.vo.MySqlVO;
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
@Api(tags = {"winEnv - mysql"})
@PathRestController("win/mysql")
@Slf4j
public class MySqlController {

    /**
     * mysql
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "安装MySQL", notes = "下载（解压版），安装，设置全局")
    @PostMapping("installOMySQL")
    public ResultVO<MySqlVO> installOMySQL(@RequestBody @Valid InstallMySqlDTO mySqlDTO) {
        // 下载文件
        String filePath = MySqlUtil.mysqlDownLoad(mySqlDTO.getFileName());
        // 解压文件
        String unZipFilePath = MySqlUtil.mysqlUnzip(mySqlDTO.getInstallPath(),
                mySqlDTO.getFileName(), filePath);
        // 安装mysql
        String mysqlEnv = MySqlUtil.settingMysqlEnv(mySqlDTO.getPort(), mySqlDTO.getPassword(),
                mySqlDTO.getServiceName(),
                unZipFilePath);

        MySqlVO mySqlVO = MySqlVO.builder()
                .installPath(unZipFilePath)
                .build();
        mySqlVO.setCommand(MySqlCommand.builder().build());
        return ResultVO.success(mySqlVO, mysqlEnv);
    }


}
