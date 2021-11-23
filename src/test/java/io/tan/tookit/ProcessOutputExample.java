//package io.tan.tookit;
//
//import cn.hutool.core.io.IoUtil;
//import com.detabes.result.result.ResultVO;
//import io.tan.tookit.util.TFileUtil;
//import io.tan.tookit.windows.constant.CMDConstant;
//import lombok.SneakyThrows;
//import org.springframework.util.ResourceUtils;
//
//import java.io.File;
//import java.io.IOException;
//
//import java.nio.charset.StandardCharsets;
//
//public class ProcessOutputExample {
//    public static void main(String[] arguments) throws IOException, InterruptedException {
//        System.out.println(getProcessOutput());
//
//    }
//
//    public static String getProcessOutput() throws IOException {
//        ProcessBuilder processBuilder = new ProcessBuilder();
//        processBuilder.redirectErrorStream(true);
//        processBuilder.command("java", "-version");
//        Process process = processBuilder.start();
////        StringBuilder processOutput = new StringBuilder();
////        try (BufferedReader processOutputReader = new BufferedReader(
////                new InputStreamReader(process.getInputStream()));) {
////            String readLine;
////            while ((readLine = processOutputReader.readLine()) != null) {
////                processOutput.append(readLine + System.lineSeparator());
////            }
////            process.waitFor();
////        }
////        return processOutput.toString().trim();
//        return IoUtil.read(process.getInputStream(), StandardCharsets.UTF_8);
//    }
//
//    @SneakyThrows
//    public ResultVO<String> installOpenResty(){
//        String openRestyName = "openresty-1.19.9.1-win64.zip";
//        String path = ResourceUtils.getURL("classpath:").getPath() + "windows";
//        File file = new File(path);
//        if(!file.exists()  && !file.isDirectory()){
//            file.mkdirs();
//        }
//        Runtime runtime = Runtime.getRuntime();
//        // 存在就不用下了
//        if(!TFileUtil.exist(path+ "/" + openRestyName)){
//            Process exec = runtime.exec(
//                    CMDConstant.DOWNLOAD_CERTUTIL+" https://openresty.org/download/" + openRestyName,
//                    null,
//                    file);
//            // 会阻塞等待进程执行完
//            int i = exec.waitFor();
//            if(i==0){
//                System.out.println("下载"+openRestyName+"成功");
//            }
//        }
//        System.out.println("2232");
////        runtime.exec("compact /u /exe:lzx "+openRestyName,null ,file);
//        return ResultVO.success("install ok");
//
//    }
//
//}
