/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.util.List;

/**
 * @author Klaus Hauschild
 */
public class ScreenMenu<E extends ScreenMenu.Entry> {

    private final List<E> menuEntries;
    private final int     top;
    private final int     left;
    private final boolean compact;
    private int           cursorPosition;

    public ScreenMenu(final List<E> menuEntries, final int top, final int left) {
        this(menuEntries, top, left, false);
    }

    public ScreenMenu(final List<E> menuEntries, final int top, final int left, final boolean compact) {
        this.menuEntries = menuEntries;
        this.top = top;
        this.left = left;
        this.compact = compact;
        while (!select().isEnabled()) {
            next();
        }
    }

    public void display(final Screen screen) {
        final TextGraphics graphics = screen.newTextGraphics();
        graphics.putString(left, top + (compact ? 1 : 2) * cursorPosition, "->");
        for (int i = 0; i < menuEntries.size(); i++) {
            E entry = menuEntries.get(i);
            if (entry.isEnabled()) {
                graphics.setForegroundColor(TextColor.ANSI.DEFAULT);
            } else {
                graphics.setForegroundColor(new TextColor.RGB(100, 100, 100));
            }
            graphics.putString(left + 4, top + i * (compact ? 1 : 2), entry.getLabel());
        }
    }

    public void next() {
        cursorPosition++;
        cursorPosition %= menuEntries.size();
        if (!select().isEnabled()) {
            next();
        }
    }

    public void previous() {
        cursorPosition--;
        if (cursorPosition == -1) {
            cursorPosition = menuEntries.size() - 1;
        }
        if (!select().isEnabled()) {
            previous();
        }
    }

    public E select() {
        return menuEntries.get(cursorPosition);
    }

    public static class Entry {

        private final String label;
        private boolean      enabled;

        public Entry(final String label) {
            this(label, true);
        }

        public Entry(final String label, final boolean enabled) {
            this.label = label;
            this.enabled = enabled;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

}
