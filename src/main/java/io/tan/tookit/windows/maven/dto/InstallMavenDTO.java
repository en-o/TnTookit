package io.tan.tookit.windows.maven.dto;

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
     * 1：配置环境变量， 0：不配置环境变量（默认1）
     */
    @ApiModelProperty(value = "1：配置环境变量， 0：不配置环境变量，（默认1）", example = "1")
    @Builder.Default
    @Max(1)
    @Min(0)
    Integer settingEnv = 1;


    /**
     *  maven3.x版本
     * link: https://dlcdn.apache.org/maven/maven-3/
     */
    @ApiModelProperty(value = "openresty版本", example = "3.6.3")
    @Builder.Default
    String maven3Version = "3.6.3";


    /**
     * 文件名称
     */
    @JsonIgnore
    String fileName;


    public String getFileName() {
        return MessageFormat.format(AppNameConstant.MAVEN3_WIN_NAME,maven3Version);
    }
}
