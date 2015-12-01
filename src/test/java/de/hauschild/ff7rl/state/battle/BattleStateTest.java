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
