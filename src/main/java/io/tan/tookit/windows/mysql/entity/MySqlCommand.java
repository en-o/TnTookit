package io.tan.tookit.windows.mysql.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * mysql命令提示
 *
 * @author tn
 * @date 2021-11-25 10:51
 */
@ApiModel(value = "mysql命令提示")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MySqlCommand implements Serializable {

    private static final long serialVersionUID = 315654089784739497L;

    @ApiModelProperty(value = "隐式mysql登录（显式：'mysql -u root -p123'）")
    @Builder.Default
    private String mySqlLogin = "mysql -u 用户名 -p;";

    @ApiModelProperty(value = "指定端口登录")
    @Builder.Default
    private String mySqlPortLogin = "mysql -P端口 -u 用户名 -p密码;";

    @ApiModelProperty(value = "远程显式mysql登录")
    @Builder.Default
    private String mySqlRemoteLogin = "mysql -h110.110.110.110 -u 用户名 -p 密码;";

    @ApiModelProperty(value = "查看mysql版本")
    @Builder.Default
    private String mySqlVersion = "select version(); or select @@version;";

    @ApiModelProperty(value = "查看mysql的端口")
    @Builder.Default
    private String mySqlPort = "show global variables like 'port';";

    @ApiModelProperty(value = "重置密码（mysql/bin）")
    @Builder.Default
    private String resetPwd = "mysqladmin -u root -p 旧密码 password 新密码;";

    @ApiModelProperty(value = "创建数据库(CREATE DATABASE `test` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';)")
    @Builder.Default
    private String createScheme = "create database <数据库名>;";

    @ApiModelProperty(value = "删除数据库")
    @Builder.Default
    private String dropScheme = "drop database if exists <数据库名>;";

    @ApiModelProperty(value = "连接数据库")
    @Builder.Default
    private String useScheme = "use <数据库名>;";

    @ApiModelProperty(value = "显示当前时间")
    @Builder.Default
    private String showTime = "select now();";

    @ApiModelProperty(value = "串接字符串")
    @Builder.Default
    private String concatStr = "select CONCAT(f_name,'-', l_name);";

    @ApiModelProperty(value = "表插入数据")
    @Builder.Default
    private String insertData = "insert into <表名> [( <字段名1>[,..<字段名n > ])] values ( 值1 )[, ( 值n )];";

    @ApiModelProperty(value = " 删除表中数据")
    @Builder.Default
    private String deleteData = "delete from 表名 where 表达式;";

    @ApiModelProperty(value = " 清空表中数据（同时重置自增长）")
    @Builder.Default
    private String dropTable = "drop table 表名;";

    @ApiModelProperty(value = " 修改表中数据 ")
    @Builder.Default
    private String updateData = "update <表名> set  <字段名1>=<新值>,… where <条件>;";

    @ApiModelProperty(value = " 增加字段 ", example = "alter table MyClass add passtest int(4) default '0'")
    @Builder.Default
    private String alterTableField = "alter table <表名> add <字段名> <类型> 其他;";

    @ApiModelProperty(value = " 删除字段 ")
    @Builder.Default
    private String dropTableField = "alter table <表名> drop  <字段名>;";

    @ApiModelProperty(value = " 修改表名 ")
    @Builder.Default
    private String renameTable = "rename table <原表名> to <新表名>;";

    @ApiModelProperty(value = " 备份数据库(mysql\\bin) - 导出整个数据库 ")
    @Builder.Default
    private String mysqldumpScheme = "mysqldump -u 用户名 -p 数据库名 > 导出的文件名;";

    @ApiModelProperty(value = " 备份数据库(mysql\\bin) - 导出一个表 ")
    @Builder.Default
    private String mysqldumpTable = "mysqldump -u 用户名 -p 数据库名 表名 > 导出的文件名;";

    @ApiModelProperty(value = " 备份数据库(mysql\\bin) - 导出一个数据库结构 ")
    @Builder.Default
    private String mysqldumpTableConstruction = "mysqldump -u user_name -p -d –add-drop-table 数据库名 > 导出的文件名;";

}
