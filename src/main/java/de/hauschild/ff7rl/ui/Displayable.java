/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.ui;

import com.googlecode.lanterna.screen.Screen;

/**
 * @author Klaus Hauschild
 */
public interface Displayable {

    void display(final Screen screen, final int top, final int left);

}
