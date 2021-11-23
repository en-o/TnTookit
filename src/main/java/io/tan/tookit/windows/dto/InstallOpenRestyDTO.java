package io.tan.tookit.windows.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * openResty
 *
 * @author tn
 * @version 1
 * @date 2021-11-23 17:13
 */
@ApiModel(value = "openResty安装")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstallOpenRestyDTO implements Serializable {

    /**
     * 安装路径
     */
    @ApiModelProperty(value = "安装路径",notes = "默认jar所在的目录",example = "D:\\project\\java\\my\\TnTookit\\target\\classes\\windows")
    String installPath;

    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称",example = "openresty-1.19.9.1-win64.zip")
    @Builder.Default
    String fileName="openresty-1.19.9.1-win64.zip";

}
