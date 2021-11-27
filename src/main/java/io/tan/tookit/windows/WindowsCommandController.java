package io.tan.tookit.windows;

import com.detabes.annotation.mapping.PathRestController;
import com.detabes.result.result.ResultVO;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.tan.tookit.util.CommandUtil;
import io.tan.tookit.util.TookitFileUtil;
import io.tan.tookit.windows.maven.MavenUtil;
import io.tan.tookit.windows.maven.dto.InstallMavenDTO;
import io.tan.tookit.windows.maven.entity.MavenCommand;
import io.tan.tookit.windows.maven.vo.MavenVO;
import io.tan.tookit.windows.mysql.MySqlUtil;
import io.tan.tookit.windows.mysql.dto.InstallMySqlDTO;
import io.tan.tookit.windows.mysql.entity.MySqlCommand;
import io.tan.tookit.windows.mysql.vo.MySqlVO;
import io.tan.tookit.windows.nginx.NginxUtil;
import io.tan.tookit.windows.nginx.dto.InstallOpenRestyDTO;
import io.tan.tookit.windows.nginx.entity.NginxCommand;
import io.tan.tookit.windows.nginx.vo.InstallOpenRestyVO;
import io.tan.tookit.windows.vo.FindPortVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.*;

/**
 * win 常用指令
 *
 * @author tn
 * @version 1
 * @date 2021-11-23 10:15
 */
@Api(tags = {"win常用指令"})
@PathRestController("win/command")
@Slf4j
@Validated
public class WindowsCommandController {

    /**
     * 根据端口杀进程
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "根据端口杀进程", notes = "kill port")
    @DeleteMapping("kill/port")
    @ApiImplicitParam(value = "端口号", name = "port", required = true, dataTypeClass = Integer.class)
    public ResultVO<String> killPort(@RequestParam @NotNull(message = "端口不能为空") Integer port) {
        String cmd = CommandUtil.commandRunStr("cmd",
                "/c",
                "for /f \"tokens=5\" %a in ('netstat /ano ^| findstr " + port + "') do taskkill /F /pid %a");

        if (StringUtils.isBlank(cmd)) {
            return ResultVO.success("端口未被占用");
        } else if (cmd.contains("成功:")) {
            return ResultVO.success(cmd.substring(cmd.indexOf("成功:"),cmd.indexOf("。")));
        }
        return ResultVO.fail(cmd);

    }


    /**
     * 根据端口查询进程名
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "根据端口查询进程名", notes = "find port")
    @DeleteMapping("find/port")
    @ApiImplicitParam(value = "端口号", name = "port", required = true, dataTypeClass = Integer.class)
    public ResultVO<List<FindPortVO>> findPort(@RequestParam @NotNull(message = "端口不能为空") Integer port) {
        String pids = CommandUtil.commandRunStr("cmd",
                "/c",
                "for /f \"tokens=5\" %a in ('netstat /ano ^| findstr " + port + "') do @echo %a");
        String[] split = StringUtils.split(pids, "\r\n");
        HashSet<String> hashSet = new HashSet(Arrays.asList(split));
        hashSet.removeIf(i -> i.equals("0"));

        List<FindPortVO> findPortVOS = new ArrayList<>();
        hashSet.forEach(pid -> {
            String pidName = CommandUtil.commandRunStr("cmd",
                    "/c",
                    "for /f \"usebackq tokens=1-5\" %a in (`tasklist ^| findstr " + pid + "`) do (@echo %a)");
            pidName = pidName.split("\r\n")[2];
            findPortVOS.add(FindPortVO.builder()
                    .pid(pid)
                    .pidName(pidName)
                    .build());
        });


        return ResultVO.success(findPortVOS, "查询成功");
    }
}
