package io.tan.tookit.windows.nginx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @ApiModelProperty(value = "安装路径", notes = "默认jar所在的目录", example = "D:\\project\\java\\my\\TnTookit\\target\\classes\\windows")
    String installPath;

    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称", example = "openresty-1.19.9.1-win64.zip")
    @Builder.Default
    String fileName = "openresty-1.19.9.1-win64.zip";


    /**
     * 注册服务
     * 1：注册服务， 0：不注册（默认0）
     */
    @ApiModelProperty(value = "1：注册服务（+自启）， 0：不注册（默认0）", example = "0")
    @Builder.Default
    @Max(1)
    @Min(0)
    Integer registerService = 0;

}
