package com.jones.mars.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerUtil {
    private static Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);
    public static Configuration cfg;
    private static Map<String, Object> initMap = new HashMap();

    public FreemarkerUtil() throws IOException {
        init();
    }

    private static void init()
            throws IOException {
        cfg = new Configuration();
        cfg.setNumberFormat("#.##");
        cfg.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
        cfg.setDateFormat("yyyy-MM-dd");
        cfg.setTimeFormat("HH:mm:ss");

        cfg.setDefaultEncoding("UTF-8");
    }

    private static Configuration getConfiguration()
            throws IOException {
        if (cfg == null)
            init();
        return cfg;
    }

    public static String process(Map<String, Object> map, String templatePath)
            throws Exception {
        return process("UTF-8", map, templatePath);
    }

    public static String process(String encoding, Map<String, Object> map, String templatePath) {
        map.putAll(initMap);
        String result = null;
        try {
            Configuration conf = getConfiguration();
            Template temp = temp = conf.getTemplate(templatePath, encoding);
            StringWriter sw = new StringWriter();
            temp.process(map, sw);
            result = sw.toString();
            sw.flush();
            sw.close();
        } catch (Exception e) {
            logger.error("根据freemarker模板 " + templatePath + " 生成html失败", e);
        }
        return result;
    }

    public static void process(Map<String, Object> map, String templatePath, String savePath) throws Exception {
        map.putAll(initMap);
        Configuration conf = getConfiguration();
        Template template = conf.getTemplate(templatePath);
        File file = new File(savePath);
        file.getParentFile().mkdirs();
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        try {
            template.process(map, osw);
            osw.flush();
            osw.close();
        } catch (Exception e) {
            logger.error("模版数据生成错误", e);
        }
    }
}

