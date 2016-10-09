/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.rooms;

import de.hauschild.ff7rl.assets.images.ScreenImage;

/**
 * @author Klaus Hauschild
 */
public class Room {

    private final ScreenImage roomScreenImage;
    private final boolean[][] walls;
    private final RoomScript  roomScript;

    public Room(final ScreenImage roomScreenImage, final boolean[][] walls, final RoomScript roomScript) {
        this.roomScreenImage = roomScreenImage;
        this.walls = walls;
        this.roomScript = roomScript;
    }

    public RoomScript getRoomScript() {
        return roomScript;
    }

    public ScreenImage getRoomScreenImage() {
        return roomScreenImage;
    }

    public boolean[][] getWalls() {
        return walls;
    }

}
