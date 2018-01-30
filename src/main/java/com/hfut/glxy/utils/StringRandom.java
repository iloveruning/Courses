package com.hfut.glxy.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class StringRandom {

    private static String[] seed={"a","9","b","1","c","d","2","e","f","3","g","h","4","j","m","5","n","p","6","s","t","7","x","y","8","z"};
    private static Random random=new Random();

    private static String[] num={"2","6","4","9","1","7","3","5","0","8"};

    @NotNull
    public static String random(int length){
        int s=seed.length;
        StringBuilder sb=new StringBuilder();
        int rand;
        for (int i = 0; i < length; i++) {
            rand=random.nextInt(s);
            sb.append(seed[rand]);
        }
        return sb.toString();
    }

    public static String generateCode(int length){
        int s=num.length;
        StringBuilder sb=new StringBuilder();
        int rand;
        for (int i = 0; i < length; i++) {
            rand=random.nextInt(s);
            sb.append(num[rand]);
        }
        return sb.toString();
    }
}
