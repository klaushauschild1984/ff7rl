/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.state.battle;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import de.hauschild.ff7rl.state.StateType;

/**
 * @author Klaus Hauschild
 */
public class BattleStateTest {

    @Test
    public void stateTypeTest() {
        assertEquals(new BattleState(null).getType(), StateType.BATTLE);
    }

}
