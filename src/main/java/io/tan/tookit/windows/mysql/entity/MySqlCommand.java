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

    @ApiModelProperty(value = "隐式登录（显式：mysql -u root -p123）")
    private String MySqlLogin = "mysql -u 用户名 -p;";

    @ApiModelProperty(value = "远程显式登录登录",notes = "root用户名")
    private String MySqlRemoteLogin = "mysql -h110.110.110.110 -u 用户名 -p 密码;";

    @ApiModelProperty(value = "查看版本")
    private String MySqlVersion = "select version(); or select @@version;";

    @ApiModelProperty(value = "重置密码（mysql/bin）")
    private String resetPwd = "mysqladmin -u root -p 旧密码 password 新密码;";

    @ApiModelProperty(value = "创建数据库(CREATE DATABASE `test` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';)")
    private String createScheme = "create database <数据库名>;";

    @ApiModelProperty(value = "删除数据库")
    private String dropScheme = "drop database if exists <数据库名>;";

    @ApiModelProperty(value = "连接数据库")
    private String useScheme = "use <数据库名>;";

    @ApiModelProperty(value = "显示当前时间")
    private String showTime = "select now();";

    @ApiModelProperty(value = "串接字符串")
    private String concatStr = "select CONCAT(f_name,'-', l_name);";

    @ApiModelProperty(value = "表插入数据")
    private String insertData = "insert into <表名> [( <字段名1>[,..<字段名n > ])] values ( 值1 )[, ( 值n )];";

    @ApiModelProperty(value = " 删除表中数据")
    private String deleteData = "delete from 表名 where 表达式;";

    @ApiModelProperty(value = " 清空表中数据（同时重置自增长）")
    private String dropTable = "drop table 表名;";

    @ApiModelProperty(value = " 修改表中数据 ")
    private String updateData = "update <表名> set  <字段名1>=<新值>,… where <条件>;";

    @ApiModelProperty(value = " 增加字段 ", example = "alter table MyClass add passtest int(4) default '0'")
    private String alterTableField = "alter table <表名> add <字段名> <类型> 其他;";

    @ApiModelProperty(value = " 删除字段 ")
    private String dropTableField = "alter table <表名> drop  <字段名>;";

    @ApiModelProperty(value = " 修改表名 ")
    private String renameTable = "rename table <原表名> to <新表名>;";

    @ApiModelProperty(value = " 备份数据库(mysql\\bin) - 导出整个数据库 ")
    private String mysqldumpScheme = "mysqldump -u 用户名 -p 数据库名 > 导出的文件名;";

    @ApiModelProperty(value = " 备份数据库(mysql\\bin) - 导出一个表 ")
    private String mysqldumpTable = "mysqldump -u 用户名 -p 数据库名 表名 > 导出的文件名;";

    @ApiModelProperty(value = " 备份数据库(mysql\\bin) - 导出一个数据库结构 ")
    private String mysqldumpTableConstruction = "mysqldump -u user_name -p -d –add-drop-table 数据库名 > 导出的文件名;";

}
