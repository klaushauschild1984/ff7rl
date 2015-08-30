/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.assets;

import org.testng.annotations.Test;

import static org.testng.Assert.fail;

/**
 * @author Klaus Hauschild
 */
public class SoundsTest {

  // TODO enable test some day
  @Test(enabled = false)
  public void playSoundTest() throws Exception {
    final Sound sound = Sounds.getSound("Prelude.mp3");
    sound.start();
    Thread.sleep(1000 * 2);
    sound.stop();
  }

  @Test
  public void errorHandlingTest() {
    try {
      Sounds.getSound("something that isn't there.wav");
      fail("Exception was expected");
    } catch (final Exception exception) {
      // exception was expected
    }
  }

}