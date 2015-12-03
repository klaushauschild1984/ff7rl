/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl;

import com.google.common.base.Throwables;

/**
 * @author Klaus Hauschild
 */
public enum Threads {

    ;

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            throw Throwables.propagate(exception);
        }
    }
}
