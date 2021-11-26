package io.tan.tookit.windows.mysql.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

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
}
