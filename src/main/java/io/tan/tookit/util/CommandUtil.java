package io.tan.tookit.util;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
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
            start.destroy();
            return true;
        } catch (InterruptedException | IOException e) {
            if(e instanceof InterruptedException){
                Thread.currentThread().interrupt();
            }
            e.printStackTrace();
            e.printStackTrace();
            log.error("运行命令:", e.getMessage());
            return false;
        }
    }

    /**
     * 运行 命令
     *
     * @param commands 命令
     * @return boolean
     */
    public static String commandRunStr(String... commands) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.redirectErrorStream(true);
            processBuilder.command(commands);
            log.info("命令：" + processBuilder.command());
            Process start = processBuilder.start();
            String result = commandGBKResult(start.getInputStream());
            log.info(result);
            start.waitFor();
            start.destroy();
            return result;
        } catch (InterruptedException | IOException e) {
            if(e instanceof InterruptedException){
                Thread.currentThread().interrupt();
            }
            e.printStackTrace();
            log.error("运行命令:", e.getMessage());
            return null;
        }
    }


    /**
     * 运行 命令
     *
     * @param commands 命令
     * @return boolean
     */
    public static String commandRunStr(File file, String... commands) {
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
            start.destroy();
            return result;
        } catch (InterruptedException | IOException e) {
            if(e instanceof InterruptedException){
                Thread.currentThread().interrupt();
            }
            e.printStackTrace();
            log.error("运行命令:", e.getMessage());
            return null;
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
            start.destroy();
            return true;
        } catch (InterruptedException | IOException e) {
            if(e instanceof InterruptedException){
                Thread.currentThread().interrupt();
            }
            e.printStackTrace();
            log.error("运行命令2:", e.getMessage());
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
            Process start = processBuilder.start();
            String result = commandGBKResult(start.getInputStream());
            log.info(result);
            start.waitFor();
            start.destroy();
            return true;
        }catch (InterruptedException | IOException e) {
            if(e instanceof InterruptedException){
                Thread.currentThread().interrupt();
            }
            e.printStackTrace();
            log.error("检查命令是否有效是否存在:", e.getMessage());
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
            Process start = processBuilder.start();
            String result = commandGBKResult(start.getInputStream());
            log.info(result);
            start.waitFor();
            start.destroy();
            return result;
        } catch (InterruptedException | IOException e) {
            if(e instanceof InterruptedException){
                Thread.currentThread().interrupt();
            }
            e.printStackTrace();
            log.error("检查命令是否有效是否存在:", e.getMessage());
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
            start.waitFor();
            start.destroy();
            return !result.contains("没有定义");
        } catch (InterruptedException | IOException e) {
            if(e instanceof InterruptedException){
                Thread.currentThread().interrupt();
            }
            e.printStackTrace();
            log.error("检查环境变量是否有效是否存在:",e.getMessage());
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


    /**
     * 解压文件
     * @param installPath  安装路径
     * @param fileName  文件名
     * @param filePath  待文件觉得路径
     * @return 解压路径
     */
    public static void unzip7Z(String installPath, String fileName, String filePath) {
        // 已经解压就不要在解压了
        // 解压插件 tools/windows/7-Zip
        String toolsPath = TookitFileUtil.getJarPathForFile().getParentFile().toString() + "\\tools\\windows\\7-Zip";
        // 解压
        if (CommandUtil.commandRun(new File(toolsPath),
                "cmd",
                "/c",
                "7z",
                "x",
                "-y",
                "-o" + TookitFileUtil.getRealFilePath(installPath),
                TookitFileUtil.getRealFilePath(filePath))) {
            log.info("文件" + fileName + "解压成功");
        } else {
            log.info("文件" + fileName + "解压失败");
        }
    }
}
