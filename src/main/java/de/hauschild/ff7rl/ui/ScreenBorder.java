/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.ui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

/**
 * @author Klaus Hauschild
 */
public class ScreenBorder {

    private final int top;
    private final int left;
    private final int width;
    private final int height;

    public ScreenBorder(final int top, final int left, final int width, final int height) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
    }

    public void display(final Screen screen) {
        TextGraphics textGraphics = screen.newTextGraphics();
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
