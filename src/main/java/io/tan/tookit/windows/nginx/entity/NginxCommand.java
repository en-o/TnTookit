package io.tan.tookit.windows.nginx.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author tn
 */
@ApiModel(value = "nginx命令提示")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NginxCommand  implements Serializable {

    private static final long serialVersionUID = 315654089784739497L;

    @ApiModelProperty(value = "测试")
    @Builder.Default
    private String test = "nginx -v";

    @ApiModelProperty(value = "启动")
    @Builder.Default
    private String startup = "nginx";

    @ApiModelProperty(value = "停止")
    @Builder.Default
    private String stop = "nginx -s stop";

    @ApiModelProperty(value = "重启")
    @Builder.Default
    private String reload = "nginx -s reload";

    @ApiModelProperty(value = "检查配置是否正确")
    @Builder.Default
    private String checkConfig = "nginx -t";

    @ApiModelProperty(value = "windows注册服务之后的启动命令",notes = "管理员模式运行命令")
    @Builder.Default
    private String windowsStartup = "net start nginx";

    @ApiModelProperty(value = "windows注册服务之后的停止命令",notes = "管理员模式运行命令")
    @Builder.Default
    private String windowsStop = "net stop  nginx";

    @ApiModelProperty(value = "linux设置自启后启动nginx服务")
    @Builder.Default
    private String linuxStartup = "systemctl start nginx.service";

    @ApiModelProperty(value = "linux设置自启后停止命令")
    @Builder.Default
    private String linuxStop = "systemctl stop nginx.service";

    @ApiModelProperty(value = "linux设置自启后重启nginx服务",notes = "常用")
    @Builder.Default
    private String linuxRestart = "systemctl restart nginx.service";

    @ApiModelProperty(value = "linux设查看Nginx进程",notes = "常用")
    @Builder.Default
    private String linuxCheckNginx = "ps -ef | grep nginx";

}