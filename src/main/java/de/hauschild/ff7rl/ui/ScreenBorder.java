/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.ui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import de.hauschild.ff7rl.context.Context;
import de.hauschild.ff7rl.context.KernelContext;

/**
 * @author Klaus Hauschild
 */
public class ScreenBorder implements Displayable {

    private final Context context;
    private final int     width;
    private final int     height;

    public ScreenBorder(final Context context, final int width, final int height) {
        this.context = context;
        this.width = width;
        this.height = height;
    }

    @Override
    public void display(final Screen screen, final int top, final int left) {
        final TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setBackgroundColor(KernelContext.getColor(context));

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
