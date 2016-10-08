/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
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
