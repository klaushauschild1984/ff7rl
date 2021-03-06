/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.sounds;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import de.hauschild.ff7rl.Threads;

/**
 * @author Klaus Hauschild
 */
public class SoundsTest {

    // TODO enable test some day
    @Test(enabled = false)
    public void playSoundTest() {
        final Sound sound = Sounds.getSound("1-01 Prelude.mp3");
        assertNotEquals(sound.getClass(), MutedSound.class);
        sound.start();
        Threads.sleep(1000 * 5);
        sound.stop();
    }

    @Test
    public void errorHandlingTest() {
        Sound sound = Sounds.getSound("something that isn't there.wav");
        assertNotNull(sound);
        assertEquals(sound.getClass(), MutedSound.class);
    }

}
