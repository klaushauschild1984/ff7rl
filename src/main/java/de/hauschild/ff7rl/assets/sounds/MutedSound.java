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
public class MutedSound implements Sound {

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void loop() {
        // nothing to do
    }

    @Override
    public void start() {
        // nothing to do
    }

    @Override
    public void stop() {
        // nothing to do
    }

    @Override
    public void pause() {
        // nothing to do
    }
}
