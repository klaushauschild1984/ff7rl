/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.rooms;

import com.googlecode.lanterna.screen.Screen;

import de.hauschild.ff7rl.assets.images.ScreenImage;
import de.hauschild.ff7rl.ui.Displayable;

/**
 * @author Klaus Hauschild
 */
public class Room implements Displayable {

    private ScreenImage roomImage;
    private boolean[][] walls;
    private RoomScript  roomScript;

    public Room(final ScreenImage roomImage, final boolean[][] walls, final RoomScript roomScript) {
        this.roomImage = roomImage;
        this.walls = walls;
        this.roomScript = roomScript;
    }

    @Override
    public void display(Screen screen, int top, int left) {
        roomImage.display(screen, top, left);
    }

    // TODO maybe x and y should be transformed into rooms space
    public boolean isBlocked(final int x, final int y) {
        return walls[x - getLeft()][y - getTop()];
    }

    public int getTop() {
        return roomScript.getTop();
    }

    public int getLeft() {
        return roomScript.getLeft();
    }

}
