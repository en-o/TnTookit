package io.tan.tookit.windows.mysql.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.tan.tookit.windows.mysql.entity.MySqlCommand;
import lombok.*;

import java.io.Serializable;

/**
 * Mysql VO
 *
 * @author tn
 * @date 2021-11-26 11:04
 */

@ApiModel(value = "MySqlVO")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MySqlVO implements Serializable {

    /**
     * 安装路径
     */
    @ApiModelProperty(value = "安装路径")
    String installPath;

    /**
     * mySQL命令提示
     */
    @ApiModelProperty(value = "mySQL命令提示")
    MySqlCommand command;
}
