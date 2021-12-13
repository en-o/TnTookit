package io.tan.tookit.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 端口相关
 *
 * @author tn
 * @version 1
 * @date 2021-12-13 10:05
 */
@Slf4j
public class FireUtil {
    /**
     * 开放 linux firewall 防火墙端口
     */
    public static void openLinuxFirewall(Integer port){
        String commands = "firewall-cmd --zone=public --add-port="+port+"/tcp --permanent";
        String str = CommandUtil.commandRunCharset(StandardCharsets.UTF_8, "bash", "-c", commands);
        log.info("开放 linux firewall 防火墙端口{}",str);
    }

    /**
     * 查看 linux firewall 防火墙 开放端口列表
     */
    public static void firewallPortList(){
        String commands = "firewall-cmd --list-ports";
        String str = CommandUtil.commandRunCharset(StandardCharsets.UTF_8, "bash", "-c", commands);
        log.info(" 查看开放端口列表{}",str);
    }

    /**
     * 查看 linux firewall 防火墙 开放端口列表
     */
    public static void firewallReload(){
        String commands = "firewall-cmd --reload";
        String str = CommandUtil.commandRunCharset(StandardCharsets.UTF_8, "bash", "-c", commands);
        log.info(" 重启firewall {}",str);
    }

}
