package io.tan.tookit.windows.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.tan.tookit.windows.entity.NginxCommand;
import lombok.*;

import java.io.Serializable;

/**
 * openResty
 *
 * @author tn
 * @version 1
 * @date 2021-11-23 17:13
 */
@ApiModel(value = "openResty")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstallOpenRestyVO implements Serializable {

    /**
     * 安装路径
     */
    @ApiModelProperty(value = "安装路径")
    String installPath;

    /**
     * 主配置文件路径文件名称
     */
    @ApiModelProperty(value = "安装路径")
    String configPath;

    /**
     * nginx命令提示
     */
    @ApiModelProperty(value = "nginx命令提示")
    NginxCommand nginxCommand;

}
