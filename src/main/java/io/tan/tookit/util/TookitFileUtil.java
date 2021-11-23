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

    public static final String FILE_SEPARATOR=System.getProperty("file.separator");

    @SneakyThrows
    public static String getWindowsDefaultPath (){
        return  ResourceUtils.getURL("classpath:").getPath() + "windows";
    }

    /**
     * 转换系统对应的的地址分隔符
     * @param path path
     * @return String
     */
    public static String getRealFilePath(String path){
        return path.replace("/",FILE_SEPARATOR).replace("\\",FILE_SEPARATOR);
    }

}
