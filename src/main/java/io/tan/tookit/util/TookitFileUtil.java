package io.tan.tookit.util;

import lombok.SneakyThrows;
import org.springframework.util.ResourceUtils;

/**
 * 文件相关util
 *
 * @author tn
 * @version 1
 * @date 2021-11-23 11:22
 */
public class TookitFileUtil  {

    @SneakyThrows
    public static String getWindowsDefaultPath (){
        return  ResourceUtils.getURL("classpath:").getPath() + "windows";
    }

}
