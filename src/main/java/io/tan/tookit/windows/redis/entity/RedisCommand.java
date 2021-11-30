package io.tan.tookit.windows.redis.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * redis常用命令
 *
 * @author tn
 * @date 2021-11-30 09:53
 */
@ApiModel(value = "redis命令提示")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisCommand {

    private static final long serialVersionUID = 315654089784739497L;

    @ApiModelProperty(value = "卸载服务(其他参数：指定服务名--service-name 服务名)")
    @Builder.Default
    private String uninstall = "redis-server.exe --service-uninstal";

    @ApiModelProperty(value = "启动服务(其他参数：指定服务名--service-name 服务名)")
    @Builder.Default
    private String startup = "redis-server.exe redis.windows.conf";

    @ApiModelProperty(value = "停止服务(其他参数：指定服务名--service-name 服务名)")
    @Builder.Default
    private String stop = "redis-server --service-stop";

    @ApiModelProperty(value = "注册服务(其他参数：指定服务名--service-name 服务名，指定端口 --port 1001)")
    @Builder.Default
    private String register = "redis-server.exe --service-install redis.windows.conf --loglevel verbose";

}
