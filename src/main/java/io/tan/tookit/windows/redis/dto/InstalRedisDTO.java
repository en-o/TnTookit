package io.tan.tookit.windows.redis.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.tan.tookit.constant.AppNameConstant;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.text.MessageFormat;

/**
 * openResty
 *
 * @author tn
 * @version 1
 * @date 2021-11-23 17:13
 */
@ApiModel(value = "redis安装")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstalRedisDTO implements Serializable {

    /**
     * 安装路径
     */
    @ApiModelProperty(value = "安装路径", notes = "默认jar所在的目录", example = "D:\\project\\java\\my\\TnTookit\\target\\classes\\windows")
    String installPath;


    /**
     * redis版本
     * link: https://github.com/tporadowski/redis/releases
     * ps: tar.gz  结尾的版本在 win中都有
     */
    @ApiModelProperty(value = "window redis（4.0.14+）版本:https://github.com/tporadowski/redis/releases", example = "5.0.14")
    @Builder.Default
    String redisVersion = "5.0.14";

    /**
     * 设置端口 默认3316
     */
    @ApiModelProperty(value = "设置端口", example = "6379")
    @Builder.Default
    String port = "6379";


    /**
     * 文件名称
     */
    @JsonIgnore
    String fileName;

    /**
     * 注册服务
     * 1：注册服务， 0：不注册（默认1）
     */
    @ApiModelProperty(value = "1：注册服务（+自启）， 0：不注册,（默认1）", example = "1")
    @Builder.Default
    @Max(1)
    @Min(0)
    Integer registerService = 1;

    /**
     * 设置服务名 默认 redis
     */
    @ApiModelProperty(value = "设置服务名", example = "redis")
    @Builder.Default
    String serviceName = "redis";



    public String getFileName() {
        return MessageFormat.format(AppNameConstant.REDIS_WIN_NAME,redisVersion);
    }
}
