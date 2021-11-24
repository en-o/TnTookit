package io.tan.tookit.windows.maven.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
}
