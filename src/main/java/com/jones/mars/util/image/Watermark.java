//package com.jones.mars.util.image;
//
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
///**
// * Created by jones on 22-2-22.
// */
//public class Watermark {
//
//    public final static BufferedImage getSourceImage(String sourceImagePath){
//        File file = new File(sourceImagePath);
//        BufferedImage image = null;
//        try {
//            Image src = ImageIO.read(file);
//            int width = src.getWidth(null);
//            int height = src.getHeight(null);
//            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            Graphics g = image.createGraphics();
//            g.drawImage(src, 0, 0, width, height, null);
//        } catch (IOException e) {
//            System.out.println("fail to get source image!");
//        }
//        return image;
//    }
//
//    public final static void outputMarkedImage(BufferedImage image, String outputPath){
//        try{
//            FileOutputStream out = new FileOutputStream(outputPath);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            encoder.encode(image);
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public final static void pressImage(String pressImg, String targetImg, int x, int y){
//        BufferedImage image = getSourceImage(targetImg);
//
//        // add water mark
//        File pressFile = new File(pressImg);
//        Image srcPress = null;
//        try {
//            srcPress = ImageIO.read(pressFile);
//        } catch (IOException e) {
//            System.out.println("fail to get press image");
//        }
//        Graphics graphics = image.getGraphics();
//        graphics.drawImage(srcPress, x, y, srcPress.getWidth(null), srcPress.getHeight(null), null);
//        graphics.dispose();
//
//        outputMarkedImage(image, targetImg);
//    }
//
////    public static void main(String[] args) {
////        Watermark.pressImage("/home/jones/images/extra_app_img.jpg", "/home/jones/images/微信图片_20220221183456.jpg", 100, 100);
////    }
//}
