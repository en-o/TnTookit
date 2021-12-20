package io.tan.tookit.controller;

import cn.jdevelops.annotation.mapping.PathRestController;
import cn.jdevelops.result.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.tan.tookit.linux.mysql.LinuxMysqlDTO;
import io.tan.tookit.util.CommandUtil;
import io.tan.tookit.util.FireUtil;
import io.tan.tookit.util.OSinfo;
import io.tan.tookit.util.TookitFileUtil;
import io.tan.tookit.windows.mysql.MySqlUtil;
import io.tan.tookit.windows.mysql.dto.InstallMySqlDTO;
import io.tan.tookit.windows.mysql.entity.MySqlCommand;
import io.tan.tookit.windows.mysql.vo.MySqlVO;
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

    /**
     * centos mysql
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "centos安装Mysql8", notes = "centos 7 Mysql8")
    @PostMapping("/centos/installMysql")
    public ResultVO<MySqlVO> installMysqlByCentos(@RequestBody @Valid LinuxMysqlDTO linux) {

        String commands = " service mysqld start";
        String mysqlExist = CommandUtil.commandRunCharset(StandardCharsets.UTF_8, "bash", "-c", commands);
        if(!mysqlExist.contains("Failed")){
            return ResultVO.fail(mysqlExist);
        }
        // jar文件夹
        String jarPath = TookitFileUtil.getJarPathForFile().getParentFile().toString();
        //  脚本文件夹
        // chmod 755 mysql_install.sh && source mysql_install.sh 8.0.26 123456 3306 /tn/mysql
        String installShell = jarPath + "/tools/linux/mysql/mysql_install.sh" + " " +
                linux.getVersion() +
                " " +
                linux.getPassword() +
                " " +
                linux.getPort() +
                " " +
                linux.getInstallPath() +
                " " +
                linux.getRmoteRoot();
        String msg = CommandUtil.commandRunCharset(StandardCharsets.UTF_8,"bash", "-c", "chmod 755 " + jarPath + "/tools/linux/mysql/mysql_install.sh"
                + " && source " + installShell);
        MySqlVO mySqlVO = MySqlVO.builder()
                .installPath(linux.getInstallPath())
                .build();
        mySqlVO.setCommand(MySqlCommand.builder().build());
        if("1".equals(linux.getFirewallPort())&&OSinfo.isLinux()){
            FireUtil.openLinuxFirewall(linux.getPort());
            FireUtil.firewallReload();
        }
        String success = "MySQL installing Successfully";
        String msgstr = "详情请查看控制台";
        if(msg.contains(success)){
            msgstr = " ========== MySQL installing Successfully =====" +
                    " \r\n" +
                    " MySQL:\r\n" +
                    " account: root\r\n" +
                    " password: "+linux.getPassword()+"\r\n" +
                    " port: "+linux.getPassword()+"\r\n" +
                    " database: "+linux.getInstallPath()+"\r\n" +
                    "  ==============================================";
        }
        return ResultVO.success(mySqlVO, msgstr);
    }

}
