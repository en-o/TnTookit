package io.tan.tookit.windows.maven.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.tan.tookit.windows.maven.entity.MavenCommand;
import lombok.*;

import java.io.Serializable;

/**
 * MAVEN
 *
 * @author tn
 * @date 2021-11-24 16:59
 */

@ApiModel(value = "mavenVO")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MavenVO  implements Serializable {

    /**
     * 安装路径
     */
    @ApiModelProperty(value = "安装路径")
    String installPath;

    /**
     * 仓库路径
     */
    @ApiModelProperty(value = "仓库路径")
    String repositoryPath;


    /**
     * setting文件路径
     */
    @ApiModelProperty(value = "setting文件路径")
    String settingPath;


    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    String remark;


    /**
     * maven命令提示
     */
    @ApiModelProperty(value = "maven命令提示")
    MavenCommand command;
}
