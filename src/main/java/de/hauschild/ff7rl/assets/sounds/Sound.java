/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.sounds;

/**
 * @author Klaus Hauschild
 */
public interface Sound {

    boolean isPlaying();

    void loop();

    void start();

    void stop();

    void pause();

}
