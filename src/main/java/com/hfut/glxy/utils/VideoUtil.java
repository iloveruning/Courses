package com.hfut.glxy.utils;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenliangliang
 * @date: 2017/12/5
 */
public class VideoUtil {

    private static final List<String> VIDEO_TYPES= Arrays.asList("mp4","avi","3gp","webm","flv","mov","wmv","asx","asf","mpg");


    /**
     * 截取视频中指定位置的图片
     * @param videoPath  视频位置
     * @param imgPath    截图存放位置
     * @param cutPoint    截图的位置（秒）
     * @return boolean
     */
    public static boolean cutImg(String videoPath,String imgPath,Integer cutPoint){

        File video=new File(videoPath);
        if (!video.exists()){
            System.out.println("路径[\" + veido_path + \"]对应的视频文件不存在!");
            return false;
        }

        List<String> commands=new ArrayList<>();
        commands.add("F:\\ChromeDownload\\ffmpeg-20171204-71421f3-win64-static\\bin\\ffmpeg.exe");
        //视频文件位置
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        //设置截取视频多少秒时的画面
        commands.add("-ss");
        commands.add(cutPoint.toString());
        //指定截取的图片大小
        commands.add("-s");
        commands.add("700x525");
        //截取的图片的保存路径
        commands.add(imgPath);

        try {
            new ProcessBuilder()
                    .command(commands)
                    .redirectErrorStream(true)
                    .start();
            System.out.println("截取成功");
            return true;
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("视频截图失败");
            return false;
        }
    }




    public static boolean check(String video){
        String[] strs= StringUtils.split(video,".");
        String suffix=strs[strs.length-1];
        return VIDEO_TYPES.contains(suffix);
    }

}
