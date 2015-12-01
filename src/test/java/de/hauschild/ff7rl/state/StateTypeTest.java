package de.hauschild.ff7rl.state;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import de.hauschild.ff7rl.state.intro.IntroState;

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
