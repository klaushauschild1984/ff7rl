/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.images;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import de.hauschild.ff7rl.ui.Displayable;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

/**
 * @author Klaus Hauschild
 */
public class ScreenImage implements Displayable {

    private final Color[][]    background;
    private final Color[][]    foreground;
    private final List<String> text;
    private final Dimension    size;

    public ScreenImage(final Color[][] background, final Color[][] foreground, final List<String> text) {
        this.background = background;
        this.foreground = foreground;
        this.text = text;
        size = new Dimension(background.length, background[0].length);
    }

    @Override
    public void display(final Screen screen, final int top, final int left) {
        TextGraphics textGraphics = screen.newTextGraphics();
        for (int x = 0; x < size.width; x++) {
            for (int y = 0; y < size.height; y++) {
                // TODO handle transparent pixels and non present text
                Color background = getBackground(x, y);
                Color foreground = getForeground(x, y);
                String text = getText(x, y);
                textGraphics.setBackgroundColor(new TextColor.RGB(background.getRed(), background.getGreen(), background
                        .getBlue()));
                textGraphics.setForegroundColor(new TextColor.RGB(foreground.getRed(), foreground.getGreen(), foreground
                        .getBlue()));
                textGraphics.putString(left + x, top + y, text);
            }
        }
    }

    private Color getBackground(final int x, final int y) {
        return background[x][y];
    }

    private Color getForeground(final int x, final int y) {
        return foreground[x][y];
    }

    private String getText(final int x, final int y) {
        return String.valueOf(text.get(y).charAt(x));
    }

}
