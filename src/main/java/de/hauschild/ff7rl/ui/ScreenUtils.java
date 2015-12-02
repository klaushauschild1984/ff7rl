/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.ui;

import com.googlecode.lanterna.graphics.TextGraphics;

/**
 * @author Klaus Hauschild
 */
public class ScreenUtils {

    private ScreenUtils() {
    }

    public static void renderBox(final TextGraphics textGraphics, final int top, final int left, final int width, final int height) {
        // place corners
        textGraphics.putString(left, top, "+");
        textGraphics.putString(left + width, top, "+");
        textGraphics.putString(left, top + height, "+");
        textGraphics.putString(left + width, top + height, "+");
        // draw lines
        for (int i = left + 1; i < left + width; i++) {
            textGraphics.putString(i, top, "-");
            textGraphics.putString(i, top + height, "-");
        }
        for (int i = top + 1; i < top + height; i++) {
            textGraphics.putString(left, i, "|");
            textGraphics.putString(left + width, i, "|");
        }
    }

}
