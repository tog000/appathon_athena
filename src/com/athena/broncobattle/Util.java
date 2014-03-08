package com.athena.broncobattle;

import android.graphics.Color;

/**
 * Created by tog on 2/25/14.
 */
public class Util {


    public static int darkenColor(int c, float amount){
        float[] hsv = new float[3];
        Color.colorToHSV(c, hsv);
        hsv[2] -= amount;
        return Color.HSVToColor(hsv);
    }

    public static int lightenColor(int c, float amount){
        float[] hsv = new float[3];
        Color.colorToHSV(c, hsv);
        hsv[2] += amount;
        return Color.HSVToColor(hsv);
    }

    public static int saturateColor(int c, float amount){
        float[] hsv = new float[3];
        Color.colorToHSV(c, hsv);
        hsv[1] += amount;
        return Color.HSVToColor(hsv);
    }

}
