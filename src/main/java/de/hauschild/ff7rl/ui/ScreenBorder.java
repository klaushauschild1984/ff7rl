/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.ui;

import com.googlecode.lanterna.TextColor.RGB;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

/**
 * @author Klaus Hauschild
 */
public class ScreenBorder implements Displayable {

    private final int width;
    private final int height;

    public ScreenBorder(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void display(Screen screen, int top, int left) {
        final TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setBackgroundColor(new RGB(0, 0, 128));

        // place corners
        textGraphics.putString(left, top, "+");
        textGraphics.putString(left + width - 1, top, "+");
        textGraphics.putString(left, top + height - 1, "+");
        textGraphics.putString(left + width - 1, top + height - 1, "+");

        // draw lines
        for (int i = left + 1; i < left + width - 1; i++) {
            textGraphics.putString(i, top, "-");
            textGraphics.putString(i, top + height - 1, "-");
        }
        for (int i = top + 1; i < top + height - 1; i++) {
            textGraphics.putString(left, i, "|");
            textGraphics.putString(left + width - 1, i, "|");
        }

        // fill inner space
        for (int x = left + 1; x < left + width - 1; x++) {
            for (int y = top + 1; y < top + height - 1; y++) {
                textGraphics.putString(x, y, " ");
            }
        }
    }

}
