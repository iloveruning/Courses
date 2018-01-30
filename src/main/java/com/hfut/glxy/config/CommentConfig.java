package com.hfut.glxy.config;

import com.hfut.glxy.utils.PropertiesUtil;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author chenliangliang
 * @date: 2017/11/30
 */
public class CommentConfig {


    private static PropertiesUtil util;


    static {
        try {
            util = new PropertiesUtil(Objects.requireNonNull(CommentConfig.class.getClassLoader().getResource("config/comment.properties")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Integer getIsCheck(String key){
        if (util==null){
            return 0;
        }
        return Integer.valueOf(util.getValue(key,"0"));
    }

    public static boolean setIsCheck(String key,Integer value) {
        return  (util != null && util.saveProperty(key, value.toString(), LocalDateTime.now().toString()));
    }



}
