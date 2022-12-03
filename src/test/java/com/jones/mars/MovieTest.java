package com.jones.mars;

import org.bytedeco.ffmpeg.global.avcodec;

import org.bytedeco.javacv.*;

import org.bytedeco.javacv.Frame;

import sun.font.FontDesignMetrics;

import java.awt.*;

import java.awt.image.BufferedImage;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;

import java.util.List;

// https://blog.csdn.net/weixin_33345090/article/details/114956851

public class MovieTest {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH🇲🇲ss");

    public static void main(String[] args) throws FrameGrabber.Exception, FrameRecorder.Exception {

// 构造测试字幕

        String[] test = {

                "世上无难事",

                "只怕有心人",

                "只要思想不滑坡",

                "办法总比困难多",

                "长江后浪推前浪",

                "前浪死在沙滩上"

        };

// 为连续的50帧设置同一个测试字幕文本

        List testStr = new ArrayList<>();

        for (int i = 0; i < 300; i++) {

            testStr.add(test[i / 50]);

        }

// 设置源视频、加字幕后的视频文件路径

        FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault("E:\\BaiduNetdiskDownload\\testout.mkv");

        grabber.start();

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("E:\\BaiduNetdiskDownload\\outtest.mkv",

                1280, 720, 2);

// 视频相关配置，取原视频配置

        recorder.setFrameRate(grabber.getFrameRate());

        recorder.setVideoCodec(grabber.getVideoCodec());

        recorder.setVideoBitrate(grabber.getVideoBitrate());

// 音频相关配置，取原音频配置

        recorder.setSampleRate(grabber.getSampleRate());

        recorder.setAudioCodec(avcodec.AV_CODEC_ID_MP3);

        recorder.start();

        System.out.println("准备开始推流...");

        Java2DFrameConverter converter = new Java2DFrameConverter();

        Frame frame;

        int i = 0;

        while ((frame = grabber.grab()) != null) {

// 从视频帧中获取图片

            if (frame.image != null) {

                BufferedImage bufferedImage = converter.getBufferedImage(frame);

// 对图片进行文本合入

                bufferedImage = addSubtitle(bufferedImage, testStr.get(i++ % 300));

// 视频帧赋值，写入输出流

                frame.image = converter.getFrame(bufferedImage).image;

                recorder.record(frame);

            }

// 音频帧写入输出流

            if(frame.samples != null) {

                recorder.record(frame);

            }

        }

        System.out.println("推流结束...");

        grabber.stop();

        recorder.stop();

    }

    /**

     * 图片添加文本

     *

     * @param bufImg

     * @param subTitleContent

     * @return

     */

    private static BufferedImage addSubtitle(BufferedImage bufImg, String subTitleContent) {

// 添加字幕时的时间

        Font font = new Font("微软雅黑", Font.BOLD, 32);

        String timeContent = sdf.format(new Date());

        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);

        Graphics2D graphics = bufImg.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

//设置图片背景

        graphics.drawImage(bufImg, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);

//设置左上方时间显示

        graphics.setColor(Color.orange);

        graphics.setFont(font);

        graphics.drawString(timeContent, 0, metrics.getAscent());

// 计算文字长度，计算居中的x点坐标

        int textWidth = metrics.stringWidth(subTitleContent);

        int widthX = (bufImg.getWidth() - textWidth) / 2;

        graphics.setColor(Color.red);

        graphics.setFont(font);

        graphics.drawString(subTitleContent, widthX, bufImg.getHeight() - 100);

        graphics.dispose();

        return bufImg;

    }

}
