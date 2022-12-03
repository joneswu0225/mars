package com.jones.mars.util;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.constant.FileType;
import com.jones.mars.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.*;

/**
 * Created by jones on 21-10-12.
 */


@Slf4j
@Component
public class KrpanoUtil {
    private static String KRPANO_LICENSE;// ="EZVqe7lJ+9/3WzTVdeX5lIy67VsivqMXr3yGfjEZKsg9HngT9p/03VVM2drp579u+ogd4NHhGLvwYyeBjCRXKVjq4N+V60sGlu6qcaEF8rwI0ZT1ZZY/xBx+TjmsSAUE9kgqEEB+KTeBREH8EfQrkeDAtRMlxO75npbAL9EV/XHj3cG1KKiarAGCoVDZmSrrZdF8dN4=";
    private static String KRPANO_HOME;// = "https://openapi.youdao.com";
    private static String KRPANO_COMMAND;// = "https://openapi.youdao.com";
    private static String KRPANO_CONFIG;// = "files/tmp/audio";
    private static Long PROCESS_KRPANO_WAIT_TIME = 100000l;

    public static String getOutPutPath(Integer blockId){
        return FileUploadService.fileUploadPath + File.separator + FileType.FILE_PATH_PREFIX + File.separator + "block" + File.separator + blockId + File.separator + "panos";
    }

    public static String getOutPutTempPath(Integer blockId){
        if(StringUtils.isEmpty(FileUploadService.fileUploadTempPath)){
            return null;
        }
        return FileUploadService.fileUploadTempPath + File.separator + FileType.FILE_PATH_PREFIX + File.separator + "block" + File.separator + blockId + File.separator + "panos";
    }

    public enum PanoType {
        FLAT("flat"), SPHERE("sphere"), CYLINDER("cylinder");
        public final String value;
        PanoType(String value) {
            this.value = value;
        }
    }

    @Value("${krpano.license}")
    public void setKrpanoLicense(String krpanoLicense) {
        KrpanoUtil.KRPANO_LICENSE = krpanoLicense;
    }
    @Value("${krpano.home}")
    public void setKrpanoHome(String krpanoHome) {
        KrpanoUtil.KRPANO_HOME = krpanoHome;
    }
    @Value("${krpano.command}")
    public void setKrpanoCommand(String krpanoCommand) {
        KrpanoUtil.KRPANO_COMMAND = krpanoCommand;
    }
    @Value("${krpano.config}")
    public void setKrpanoConfig(String krpanoConfig) {
        KrpanoUtil.KRPANO_CONFIG = krpanoConfig;
    }

    public static void copyFiles(File sourceFile, File destFile) {
        String fileName = sourceFile.getName();
        if(sourceFile.isFile()){
            try {
                Files.move(sourceFile.toPath(), destFile.isFile() ? destFile.toPath() : Paths.get(destFile.getAbsolutePath(), fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Paths.get(destFile.getAbsolutePath(), sourceFile.getName()).toFile().mkdirs();
            for (File file : sourceFile.listFiles()) {
                copyFiles(file, Paths.get(destFile.getAbsolutePath(), fileName).toFile());
            }
        }
    }


    public static ErrorCode executeCommand(String command){
        ErrorCode message = ErrorCode.OK;
        try {
            log.info("execute command: \n " + command);
            Process pro = Runtime.getRuntime().exec(command);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<String> future = executorService.submit(() -> { return handleProcessBlock(pro.getInputStream());});
            executorService.shutdown();
            if(pro.waitFor(PROCESS_KRPANO_WAIT_TIME, TimeUnit.MILLISECONDS)){
                if(pro.exitValue() != 0){
                    message = ErrorCode.KRPANO_SOURCE_IMAGE_NOT_EXIST;
                    log.info("Failed to call shell: " + future.get() + "command:" + command);
                } else {
                    log.info("succeed in execute command: \n " + command);
                }
            } else {
                message = ErrorCode.KRPANO_SLICE_PROCESS_TIMEOUT;
                log.error("Failed to call shell, for timeout: " + command);
                executorService.shutdownNow();
                pro.destroy();
            }
        } catch (IOException e) {
            message = ErrorCode.KRPANO_SOURCE_IMAGE_NOT_EXIST;
            log.error("fail to load source file: " + command);
        } catch (InterruptedException e) {
            message = ErrorCode.KRPANO_SLICE_PROCESS_INTERRUPTED;
            log.error("command process has been interrupted: " + command);
        } catch (ExecutionException e) {
            message = ErrorCode.KRPANO_SLICE_PROCESS_INTERRUPTED;
            log.error("command process has been interrupted: " + command);
        }
        return message;
    }


    public static ErrorCode slice(String fileName, Integer blockId,  PanoType panoType){
        String tmpPath = getOutPutTempPath(blockId);
        if (tmpPath == null) {
            return slice(fileName, getOutPutPath(blockId), panoType);
        }
        // 切图到临时目录
        String finalPath = getOutPutPath(blockId) + File.separator + fileName.substring(fileName.lastIndexOf("/")+1, fileName.lastIndexOf(".")) + ".tiles";
        ErrorCode code = slice(fileName, tmpPath, panoType);
        tmpPath = tmpPath + File.separator + fileName.substring(fileName.lastIndexOf("/")+1, fileName.lastIndexOf(".")) + ".tiles";
        //　临时目录移动到实际目录
        if (code.equals(ErrorCode.OK)) {
            if(Paths.get(tmpPath).toFile().exists()) {
                moveFile(tmpPath, finalPath);
            } else {
                log.error("sliced file cannot be found in tmp path : " + tmpPath);
            }
        }
        return code;
    }

    private static String handleProcessBlock(InputStream inputStream){
        BufferedReader br = null;
        StringBuffer strbr = new StringBuffer();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null){
                strbr.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strbr.toString();
    }

    public static ErrorCode slice(String fileName, String outputPath, PanoType panoType){
        StringBuilder sb = new StringBuilder(" " + KrpanoUtil.KRPANO_HOME + "/").append(KrpanoUtil.KRPANO_COMMAND)
                .append(" -config=" + KrpanoUtil.KRPANO_HOME + "/" + KrpanoUtil.KRPANO_CONFIG)
                .append(" -license=" + KrpanoUtil.KRPANO_LICENSE)
                .append(" -outputpath=" + outputPath)
                .append(" -panotype=" + panoType).append(" " + fileName);

        return executeCommand(sb.toString());
    }

    public static ErrorCode mkdirs(String path){
        return executeCommand("mkdir -p " + path);
    }

    public static ErrorCode removeFile(String file){
        return executeCommand("rm -rf " + file);
    }

    public static ErrorCode clearFile(String file){
        File tmpFile = new File(file);
        return executeCommand(String.format("rm -rf %s removeFile", tmpFile.getAbsolutePath(), tmpFile.getParentFile().getAbsolutePath()));
    }

    public static ErrorCode moveFile(String sourceFile, String destFile){
        removeFile(destFile);
        String parentPath = new File(destFile).getParent();
        mkdirs(parentPath);
        return executeCommand("mv " + sourceFile + " " + parentPath);
    }


    private static boolean deleteFile(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteFile(children[i]);
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

//    public static void main(String[] args) throws IOException {
////        Long blockId = 22l;
////        String fileName = "/media/jones/1f99b686-b16b-43ad-8aff-c7ec2cbfffc7/projects/pano/images/thumb_tour3d.jpg";
////        String outputPath = "%INPUTPATH%/panos/block_id_" + blockId;
////        ErrorCode result = KrpanoUtil.slice(fileName, outputPath, PanoType.FLAT);
////        System.out.println(result.description);
//        Integer blockId=3;
//        String fileName = "files/upload/static/block/3/scene_pano_img/s1635945028000.jpg";
//        String tmpPath = "./pano_image_tmp/static//block/3/panos";
//        String finalPath = "files/upload/static//block/3/panos/s1635945028000.tiles";
//        System.out.println(fileName.substring(fileName.lastIndexOf("/")+1, fileName.lastIndexOf(".")));
//        ErrorCode code = slice(fileName, tmpPath, PanoType.FLAT);
//        tmpPath = tmpPath + File.separator + fileName.substring(fileName.lastIndexOf("/")+1, fileName.lastIndexOf(".")) + ".tiles";
//        deleteFile(Paths.get(finalPath).toFile());
//        if(Paths.get(tmpPath).toFile().exists()) {
//            Paths.get(finalPath).toFile().getParentFile().mkdirs();
//            Files.move(Paths.get(tmpPath), Paths.get(finalPath), StandardCopyOption.REPLACE_EXISTING);
//        } else {
//            System.out.println("file cannot be found in tmp path: " + tmpPath);
//        }
//    }



}
