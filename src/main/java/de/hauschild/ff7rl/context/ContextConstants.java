/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.context;

/**
 * @author Klaus Hauschild
 */
public interface ContextConstants {

    interface General {

        String LAST_STATE = "ff7rl.context.general.last-state";
        String REGION     = "ff7rl.context.general.region";
        String STEPS      = "ff7rl.context.general.steps";
        String GIL        = "ff7rl.context.general.gil";
    }

    interface Room {

        String ROOM = "ff7rl.context.room.name";

    }

}
