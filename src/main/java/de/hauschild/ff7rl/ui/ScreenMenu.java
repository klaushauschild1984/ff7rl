/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.ui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

/**
 * @author Klaus Hauschild
 */
public class ScreenMenu<ENTRIES> {

  private final ENTRIES[] menuEntries;
  private final int top;
  private final int left;

  private int cursorPosition;

  public ScreenMenu(final ENTRIES[] menuEntries, final int top, final int left) {
    this.menuEntries = menuEntries;
    this.top = top;
    this.left = left;
  }

  public void display(final Screen screen) {
    final TextGraphics graphics = screen.newTextGraphics();
    graphics.putString(left, top + 2 * cursorPosition, "->");
    for (int i = 0; i < menuEntries.length; i++) {
      ENTRIES entry = menuEntries[i];
      graphics.putString(left + 4, top + i * 2, entry.toString());
    }
  }

  public void next() {
    cursorPosition++;
    cursorPosition %= menuEntries.length;
  }

  public void previous() {
    cursorPosition--;
    if (cursorPosition == -1) {
      cursorPosition = menuEntries.length - 1;
    }
  }

  public ENTRIES select() {
    return menuEntries[cursorPosition];
  }

}
