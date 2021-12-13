package io.tan.tookit;

import io.tan.tookit.util.FireUtil;
import io.tan.tookit.util.OSinfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tnnn
 */
@SpringBootApplication
@Slf4j
public class TnTookitApplication {

    public static void main(String[] args) {
        SpringApplication.run(TnTookitApplication.class, args);
        if(OSinfo.isLinux()){
            FireUtil.openLinuxFirewall(80);
            FireUtil.openLinuxFirewall(443);
            FireUtil.openLinuxFirewall(8080);
            FireUtil.firewallReload();
            FireUtil.firewallPortList();
        }
    }


}
