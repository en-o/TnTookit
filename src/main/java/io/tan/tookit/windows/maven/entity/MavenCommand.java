package io.tan.tookit.windows.maven.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * maven常会用命令
 *
 * @author tn
 * @date 2021-11-25 10:51
 */
@ApiModel(value = "maven命令提示")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MavenCommand  implements Serializable {

    private static final long serialVersionUID = 315654089784739497L;

    @ApiModelProperty(value = "查看版本")
    private String mavenVersion = "mvn -version";

    @ApiModelProperty(value = "测试")
    private String mavenTest = "mvn test";

    @ApiModelProperty(value = "打包")
    private String mavenPackage = "mvn package";

    @ApiModelProperty(value = "将依赖添加到本地仓库")
    private String mavenInstall = "mvn install";

    @ApiModelProperty(value = "示例: 将jar添加到本地仓库")
    private String mavenInstallJAR = "mvn install:install-file -DgroupId=com.xinyartech -DartifactId=easyexcel -Dversion=1.1.1 -Dpackaging=jar -Dfile=/root/shell/easyexcel-1.1.1.jar   ";

    @ApiModelProperty(value = "清除")
    private String mavenClean = "mvn clean";

    @ApiModelProperty(value = "先清除在打包")
    private String mavenCleanPackage = "mvn clean package";

    @ApiModelProperty(value = "先清除在打包并跳过测试")
    private String mavenCleanPackageTryTest = "mvn clean package  -Dmaven.test.skip=true ";
}
