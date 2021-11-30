package io.tan.tookit.windows.redis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.tan.tookit.windows.redis.entity.RedisCommand;
import lombok.*;

import java.io.Serializable;

/**
 * redis视图返回
 *
 * @author tn
 * @date 2021-11-30 09:52
 */
@ApiModel(value = "RedisVO")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisVO  implements Serializable {

    /**
     * 安装路径
     */
    @ApiModelProperty(value = "安装路径")
    String installPath;


    /**
     * 配置文件路径
     */
    @ApiModelProperty(value = "配置文件路径")
    String configFile;

    /**
     * redis命令提示
     */
    @ApiModelProperty(value = "redis命令提示")
    RedisCommand command;
}
