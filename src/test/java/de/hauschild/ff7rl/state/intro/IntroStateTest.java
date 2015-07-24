package de.hauschild.ff7rl.state.intro;

import de.hauschild.ff7rl.state.StateType;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Klaus Hauschild
 */
public class IntroStateTest {

  @Test
  public void stateTypeTest() {
    assertEquals(new IntroState(null).getType(), StateType.INTRO);
  }

}