package io.tan.tookit.windows.mysql.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 添加mysql用户
 *
 * @author tn
 * @date 2021-11-29 15:56
 */
@ApiModel(value = "MySql安装")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddMySqlUserVDTO {

    /**
     * mysql端口 默认3316
     */
    @ApiModelProperty(value = "mysql端口", example = "3316")
    @Builder.Default
    String port = "3316";

    /**
     * mysql用户  默认root
     */
    @ApiModelProperty(value = "mysql用户", example = "root")
    @Builder.Default
    String userName = "root";


    /**
     * mysql密码  默认root
     */
    @ApiModelProperty(value = "mysql密码", example = "root")
    @Builder.Default
    String password = "root";
}
