package io.tan.tookit.windows.maven.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * maven
 *
 * @author tn
 * @date 2021-11-24 16:59
 */
@ApiModel(value = "maven安装")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstallMavenDTO implements Serializable {

    /**
     * 安装路径
     */
    @ApiModelProperty(value = "安装路径", notes = "默认jar所在的目录", example = "D:\\project\\java\\my\\TnTookit\\target\\classes\\windows")
    String installPath;

    /**
     * 配置环境变量
     * 1：配置环境变量， 0：不配置环境变量（默认0）
     */
    @ApiModelProperty(value = "1：配置环境变量， 0：不配置环境变量（默认0）", example = "0")
    @Builder.Default
    @Max(1)
    @Min(0)
    Integer settingEnv = 0;

}
