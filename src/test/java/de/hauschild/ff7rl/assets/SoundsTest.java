package de.hauschild.ff7rl.assets;

import org.testng.annotations.Test;

import static org.testng.Assert.fail;

/**
 * @author Klaus Hauschild
 */
public class SoundsTest {

  @Test
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