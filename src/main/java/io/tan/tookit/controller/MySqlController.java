package io.tan.tookit.controller;

import cn.jdevelops.annotation.mapping.PathRestController;
import cn.jdevelops.result.result.ResultVO;
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
 * 环境部署
 *
 * @author tn
 * @version 1
 * @date 2021-11-23 10:15
 */
@Api(tags = {"mysql"})
@PathRestController("mysql")
@Slf4j
public class MySqlController {

    /**
     * win10 mysql
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "win10安装MySQL", notes = "下载（解压版），安装，设置全局")
    @PostMapping("/win/installMySql")
    public ResultVO<MySqlVO> installMySqlByWin(@RequestBody @Valid InstallMySqlDTO mySqlDTO) {
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
