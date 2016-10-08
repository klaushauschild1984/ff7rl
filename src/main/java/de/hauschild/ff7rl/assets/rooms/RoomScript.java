/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.rooms;

import de.hauschild.ff7rl.context.Context;

/**
 * @author Klaus Hauschild
 */
public interface RoomScript {

    void initialize(Context context);

    int getTop();

    int getLeft();

    void enter();

    int update();

    void leave();

}
