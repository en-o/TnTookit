package io.tan.tookit.util;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 命令工具類
 *
 * @author tn
 * @version 1
 * @date 2021-11-23 14:52
 */
@Slf4j
public class CommandUtil {

    /**
     * 运行 命令
     *
     * @param commands 命令
     * @return boolean
     */
    public static boolean commandRun(String... commands) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.redirectErrorStream(true);
            processBuilder.command(commands);
            log.info("命令：" + processBuilder.command());
            Process start = processBuilder.start();
            String result = commandGBKResult(start.getInputStream());
            log.info(result);
            start.waitFor();
            return true;
        } catch (Exception er) {
            log.error("运行命令:", er);
            return false;
        }
    }

    /**
     * 运行 命令
     *
     * @param  file 运行目录
     * @param commands 命令
     * @return boolean
     */
    public static boolean commandRun(File file, String... commands) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.redirectErrorStream(true);
            processBuilder.command(commands);
            processBuilder.directory(file);
            log.info("命令：" + processBuilder.command());
            Process start = processBuilder.start();
            String result = commandGBKResult(start.getInputStream());
            log.info(result);
            start.waitFor();
            return true;
        } catch (Exception e) {
            log.error("运行命令2:", e);
        }
        return false;
    }

    /**
     * 检查命令是否有效是否存在
     *
     * @param commands 命令
     * @return boolean
     */
    public static boolean commandExist(String... commands) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.redirectErrorStream(true);
            processBuilder.command(commands);
            String result = commandGBKResult(processBuilder.start().getInputStream());
            log.info(result);
            return true;
        } catch (Exception e) {
            log.error("检查命令是否有效是否存在:", e);
        }
        return false;
    }

    /**
     * 检查命令是否有效是否存在
     *
     * @param commands 命令
     * @return boolean
     */
    public static String commandExistForStr(String... commands) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.redirectErrorStream(true);
            processBuilder.command(commands);
            String result = commandGBKResult(processBuilder.start().getInputStream());
            log.info(result);
            return result;
        } catch (Exception e) {
            log.error("检查命令是否有效是否存在:", e);
        }
        return null;
    }


    /**
     * 检查环境变量是否有效是否存在 和 设置全局变量
     * 检查：set java
     * 设置：set java=变量内容
     * @param temps 变量名
     * @return boolean
     */
    public static boolean envExist(String temps) {
        try {
            List<String> commands = new ArrayList<>();
            commands.add("cmd");
            commands.add("/c");
            commands.add("set");
            if(null != temps){
                commands.add(temps);
            }
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(commands);
            processBuilder.redirectErrorStream(true);
            log.info("命令："+processBuilder.command());
            Process start = processBuilder.start();
            String result = commandGBKResult(start.getInputStream());
            log.info(result);
            return !result.contains("没有定义");
        } catch (Exception e) {
            log.error("检查环境变量是否有效是否存在:",e);
        }
        return false;
    }

    /**
     * 输出 Process 返回的内容
     *
     * @param inputStream inputStream
     * @return message
     */
    public static String commandGBKResult(InputStream inputStream) {
        return IoUtil.read(inputStream, Charset.forName("GBK"));
    }
}
