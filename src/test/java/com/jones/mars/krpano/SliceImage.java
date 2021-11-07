package com.jones.mars.krpano;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jones on 21-10-12.
 */
public class SliceImage {

    public static boolean slice(String fileName, String outputPath, String panoType){
        String license = "EZVqe7lJ+9/3WzTVdeX5lIy67VsivqMXr3yGfjEZKsg9HngT9p/03VVM2drp579u+ogd4NHhGLvwYyeBjCRXKVjq4N+V60sGlu6qcaEF8rwI0ZT1ZZY/xBx+TjmsSAUE9kgqEEB+KTeBREH8EfQrkeDAtRMlxO75npbAL9EV/XHj3cG1KKiarAGCoVDZmSrrZdF8dN4=";
        String panoHome = "/media/jones/1f99b686-b16b-43ad-8aff-c7ec2cbfffc7/software/krpano/krpano-1.20.10";
        String cmd = "krpanotools makepano";
        String config = "templates/vtour-customize.config";
        StringBuilder sb = new StringBuilder(" " + panoHome + "/").append(cmd)
                .append(" -config=" + panoHome + "/" + config)
                .append(" -license=" + license)
                .append(" -outputpath=" + outputPath)
                .append(" -panotype=" + panoType).append(" " + fileName);
        try {
            Process pro = Runtime.getRuntime().exec(sb.toString());
            int status = pro.waitFor();
            if(status != 0){
                System.out.println("Failed to call shell: " + sb.toString() );
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            StringBuffer strbr = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null){
                strbr.append(line).append("\n");
            }
            String result = strbr.toString();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static void main(String[] args) {
        Long blockId = 22l;
        String panoType = "flat"; // flat,sphere,cylinder
        String fileName = "/media/jones/1f99b686-b16b-43ad-8aff-c7ec2cbfffc7/projects/pano/images/thumb_tour3d.jpg";
        String outputPath = "%INPUTPATH%/panos/block_id_" + blockId;
        boolean result = SliceImage.slice(fileName, outputPath, panoType);
        System.out.println(result);
    }
}
