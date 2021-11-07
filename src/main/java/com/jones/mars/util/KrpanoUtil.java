package com.jones.mars.util;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.constant.FileType;
import com.jones.mars.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
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

    public static ErrorCode slice(String fileName, Integer blockId,  PanoType panoType){
        return slice(fileName, getOutPutPath(blockId), panoType);
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
        ErrorCode message = ErrorCode.OK;
        StringBuilder sb = new StringBuilder(" " + KrpanoUtil.KRPANO_HOME + "/").append(KrpanoUtil.KRPANO_COMMAND)
                .append(" -config=" + KrpanoUtil.KRPANO_HOME + "/" + KrpanoUtil.KRPANO_CONFIG)
                .append(" -license=" + KrpanoUtil.KRPANO_LICENSE)
                .append(" -outputpath=" + outputPath)
                .append(" -panotype=" + panoType).append(" " + fileName);
        try {
            log.info("execute command: \n " + sb.toString());
            Process pro = Runtime.getRuntime().exec(sb.toString());
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<String> future = executorService.submit(() -> { return handleProcessBlock(pro.getInputStream());});
            executorService.shutdown();
            if(pro.waitFor(PROCESS_KRPANO_WAIT_TIME, TimeUnit.MILLISECONDS)){
                if(pro.exitValue() != 0){
                    message = ErrorCode.KRPANO_SOURCE_IMAGE_NOT_EXIST;
                    System.out.println("Failed to call shell: " + future.get() );
                }
            } else {
                message = ErrorCode.KRPANO_SLICE_PROCESS_TIMEOUT;
                log.error("Failed to call shell, for timeout: " + sb.toString() );
                executorService.shutdownNow();
                pro.destroy();
            }
        } catch (IOException e) {
            message = ErrorCode.KRPANO_SOURCE_IMAGE_NOT_EXIST;
            log.error("fail to load source image: " + fileName);
        } catch (InterruptedException e) {
            message = ErrorCode.KRPANO_SLICE_PROCESS_INTERRUPTED;
            log.error("slice process has been interrupted: " + fileName);
        } catch (ExecutionException e) {
            message = ErrorCode.KRPANO_SLICE_PROCESS_INTERRUPTED;
            log.error("slice process has been interrupted: " + fileName);
        }
        return message;
    }


    public static void main(String[] args) {
        Long blockId = 22l;
        String fileName = "/media/jones/1f99b686-b16b-43ad-8aff-c7ec2cbfffc7/projects/pano/images/thumb_tour3d.jpg";
        String outputPath = "%INPUTPATH%/panos/block_id_" + blockId;
        ErrorCode result = KrpanoUtil.slice(fileName, outputPath, PanoType.FLAT);
        System.out.println(result.description);
    }
}
