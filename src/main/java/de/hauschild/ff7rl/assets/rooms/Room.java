/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.rooms;

import de.hauschild.ff7rl.assets.images.Image;

/**
 * @author Klaus Hauschild
 */
public class Room {

    private final Image       roomImage;
    private final boolean[][] walls;
    private final RoomScript  roomScript;

    public Room(final Image roomImage, final boolean[][] walls, final RoomScript roomScript) {
        this.roomImage = roomImage;
        this.walls = walls;
        this.roomScript = roomScript;
    }

    public RoomScript getRoomScript() {
        return roomScript;
    }

    public Image getRoomImage() {
        return roomImage;
    }

    public boolean[][] getWalls() {
        return walls;
    }

}
