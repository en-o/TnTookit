package io.tan.tookit.windows.mysql.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.tan.tookit.constant.AppNameConstant;
import lombok.*;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 安装MySql
 *
 * @author tn
 * @date 2021-11-26 11:01
 */
@ApiModel(value = "MySql安装")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstallMySqlDTO implements Serializable {

    /**
     * 安装路径
     */
    @ApiModelProperty(value = "安装路径", notes = "默认jar所在的目录", example = "D:\\project\\java\\my\\TnTookit\\target\\classes\\windows")
    String installPath;

    /**
     * mysql8.x版本
     * https://mirrors.huaweicloud.com/mysql/Downloads/MySQL-8.0/
     */
    @ApiModelProperty(value = "mysql8版本:https://mirrors.huaweicloud.com/mysql/Downloads/MySQL-8.0/", example = "8.0.25")
    @Builder.Default
    String mysql8Version = "8.0.25";

    /**
     * 设置端口 默认3316
     */
    @ApiModelProperty(value = "设置端口", example = "3316")
    @Builder.Default
    String port = "3316";

    /**
     * 设置密码  默认root
     */
    @ApiModelProperty(value = "设置密码", example = "root")
    @Builder.Default
    String password = "root";

    /**
     * 设置服务名 默认 mysql
     */
    @ApiModelProperty(value = "设置服务名", example = "mysql")
    @Builder.Default
    String serviceName = "mysql";

    /**
     * 文件名称
     */
    @JsonIgnore
    String fileName;


    public String getFileName() {
        return MessageFormat.format(AppNameConstant.MYSQL8_WIN_NAME, mysql8Version);
    }
}
