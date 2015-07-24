package de.hauschild.ff7rl.state;

import de.hauschild.ff7rl.state.intro.IntroState;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Klaus Hauschild
 */
public class StateTypeTest {

  @Test
  public void getStateTest() {
    final State state = StateType.INTRO.getState(null);
    assertTrue(state.getClass() == IntroState.class);
  }

}