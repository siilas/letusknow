package br.com.silas.letusknow.utils;

import android.graphics.Color;

import java.util.Random;

public class ColorUtils {

    private ColorUtils() {
    }

    public static int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

}
