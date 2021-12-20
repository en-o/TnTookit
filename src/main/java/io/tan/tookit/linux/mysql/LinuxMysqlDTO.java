package io.tan.tookit.linux.mysql;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * linux_MySql安装
 *
 * @author tn
 * @version 1
 * @date 2021-12-20 10:17
 */
@ApiModel(value = "linux_MySql安装")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinuxMysqlDTO {

    /**
     * 安装路径
     */
    @ApiModelProperty(value = "安装路径", required = true, example = " /tn/mysql")
    @NotBlank
    String installPath;

    /**
     * 版本
     */
    @ApiModelProperty(value = "-linux-glibc2.12-x86_64.tar.xz 版本：https://mirrors.tuna.tsinghua.edu.cn/mysql/downloads/MySQL-8.0/", example = "8.0.26")
    @Builder.Default
    String version = "8.0.26";


    /**
     * 设置端口 默认3316
     */
    @ApiModelProperty(value = "设置端口", example = "3316")
    @Builder.Default
    Integer port = 3316;

    /**
     * 设置密码  默认root
     */
    @ApiModelProperty(value = "设置密码", example = "root")
    @Builder.Default
    String password = "root";


    /**
     * 是否开放root 的 % 远程连接权限（1:开，其他不开）
     */
    @ApiModelProperty(value = "是否开放root 的 % 远程连接权限（1:开，其他不开）", example = "1")
    @Builder.Default
    String rmoteRoot = "1";

    /**
     * 是否开放mysql端口的防火墙（1:开，其他不开）
     */
    @ApiModelProperty(value = "是否开放mysql端口的防火墙（1:开，其他不开）", example = "1")
    @Builder.Default
    String firewallPort = "1";

}
