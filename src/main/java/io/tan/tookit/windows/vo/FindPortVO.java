package io.tan.tookit.windows.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 根据端口查询进程名
 *
 * @author tn
 * @version 1
 * @date 2021-11-27 21:13
 */
@ApiModel(value = "根据端口查询进程名返回结果")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindPortVO implements Serializable {

    /**
     * pid
     */
    @ApiModelProperty(value = "进程号")
    String pid;


    /**
     * pidName
     */
    @ApiModelProperty(value = "进程名")
    String pidName;
}
