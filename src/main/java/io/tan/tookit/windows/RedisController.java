package io.tan.tookit.windows;

import cn.jdevelops.annotation.mapping.PathRestController;
import cn.jdevelops.result.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.tan.tookit.windows.redis.RedisUtil;
import io.tan.tookit.windows.redis.dto.InstalRedisDTO;
import io.tan.tookit.windows.redis.entity.RedisCommand;
import io.tan.tookit.windows.redis.vo.RedisVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Api(tags = {"winEnv - redis"})
@PathRestController("win/redis")
@Slf4j
public class RedisController {

    /**
     * 安装Redis
     *
     * @return message
     */
    @SneakyThrows
    @ApiOperation(value = "安装Redis", notes = "redis的下载，注册服务跟设置服务自启")
    @PostMapping("installRedis")
    public ResultVO<RedisVO> installRedis(@RequestBody @Valid InstalRedisDTO redisDTO) {
        // 下载文件
        String filePath = RedisUtil.redisDownLoad(redisDTO.getFileName(), redisDTO.getRedisVersion());
        // 解压
        String unZipFilePath = RedisUtil.redisUnzip(redisDTO.getInstallPath(), redisDTO.getFileName(), filePath);
        // TODO: 2021/11/29 redis 注册服务 - 未测试
        RedisVO.RedisVOBuilder command = RedisVO.builder().command(RedisCommand.builder().build());
        if (redisDTO.getRegisterService() == 1) {
            String installService = RedisUtil.addService(redisDTO.getPort(), redisDTO.getPassword(),
                    redisDTO.getServiceName(),
                    unZipFilePath);
            if (StringUtils.contains(installService, "requirepass")) {
                command.command(RedisCommand.builder().build())
                        .installPath(unZipFilePath)
                        .configFile(unZipFilePath + "\\redis.windows.conf");
                String[] lines = installService.split("\r\n");
                return ResultVO.success(command.build(), "安装服务成功，" +
                        "不能在同一个配置文件中设置两次密码（如果要按两个请更换安装目录），" +
                        "原密码为："
                        + lines[1]);
            } else if (StringUtils.contains(installService, "service successfully started")) {
                command.command(RedisCommand.builder().build())
                        .installPath(unZipFilePath)
                        .configFile(unZipFilePath + "\\redis.windows.conf");
                return ResultVO.success(command.build(), "安装服务成功");
            } else {
                return ResultVO.fail(installService);
            }
        }
        command.installPath(unZipFilePath);
        return ResultVO.success(command.build(), "redis下载解压成功");
    }

}
