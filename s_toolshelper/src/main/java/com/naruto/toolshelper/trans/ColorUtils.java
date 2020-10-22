package com.naruto.toolshelper.trans;

import java.util.Random;

/***
 int itemColor = Color.parseColor(ColorUtils.getRandomColor());
 **/
public class ColorUtils {
    public static String getRandomColor() {
        String R, G, B;
        Random random = new Random();
        int randPos = random.nextInt(3);//0-2
        int randR = randPos == 0 ? 125+random.nextInt(125) : random.nextInt(256);
        int randG = randPos == 1 ? 125+random.nextInt(125) : random.nextInt(256);
        int randB = randPos == 2 ? 125+random.nextInt(125) : random.nextInt(256);
        R = Integer.toHexString(randR).toUpperCase();
        G = Integer.toHexString(randG).toUpperCase();
        B = Integer.toHexString(randB).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;

        return "#" + R + G + B;
    }
}
