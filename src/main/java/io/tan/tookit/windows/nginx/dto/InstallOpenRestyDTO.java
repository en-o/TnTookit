package io.tan.tookit.windows.nginx.dto;

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
     * openresty版本
     * link: http://openresty.org/cn/download.html
     * ps: tar.gz  结尾的版本在 win中都有
     */
    @ApiModelProperty(value = "openresty版本", example = "1.19.9.1")
    @Builder.Default
    String openrestyVersion = "1.19.9.1";

    /**
     * 系统位数 （win64/win32）
     *
     */
    @ApiModelProperty(value = "系统位数（win64/win32）", example = "win64",notes = "win64/win32")
    @Builder.Default
    String systemBits = "win64";


    /**
     * 文件名称
     */
    @JsonIgnore
    String fileName;

    /**
     * 注册服务
     * 1：注册服务， 0：不注册（默认0）
     */
    @ApiModelProperty(value = "1：注册服务（+自启）， 0：不注册,（默认1）", example = "1")
    @Builder.Default
    @Max(1)
    @Min(0)
    Integer registerService = 1;


    public String getFileName() {
        return MessageFormat.format(AppNameConstant.NGINX_OPENRESTY_WIN_NAME,openrestyVersion,systemBits);
    }
}
