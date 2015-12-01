package de.hauschild.ff7rl.state.intro;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import de.hauschild.ff7rl.state.StateType;

/**
 * @author Klaus Hauschild
 */
public class IntroStateTest {

    @Test
    public void stateTypeTest() {
        assertEquals(new IntroState(null).getType(), StateType.INTRO);
    }

}
