package io.tan.tookit.windows;

import com.detabes.annotation.mapping.PathRestController;
import com.detabes.result.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.tan.tookit.windows.redis.RedisUtil;
import io.tan.tookit.windows.redis.dto.InstalRedisDTO;
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
    public ResultVO<String> installRedis(@RequestBody @Valid InstalRedisDTO redisDTO) {
        // 下载文件
        String filePath = RedisUtil.redisDownLoad(redisDTO.getFileName(), redisDTO.getRedisVersion());
        // 解压
        String unZipFilePath = RedisUtil.redisUnzip(redisDTO.getInstallPath(), redisDTO.getFileName(), filePath);
        // TODO: 2021/11/29 redis 注册服务 - 未测试
        if (redisDTO.getRegisterService() == 1) {
            String password = RedisUtil.addService(redisDTO.getPort(), "password",
                    redisDTO.getServiceName(),
                    unZipFilePath);
            return ResultVO.success(password, "安装服务成功");
        }
        return ResultVO.success(unZipFilePath, "redis下载解压成功");
    }


}
