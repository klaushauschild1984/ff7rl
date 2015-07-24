package de.hauschild.ff7rl.state.battle;

import de.hauschild.ff7rl.state.StateType;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Klaus Hauschild
 */
public class BattleStateTest {

  @Test
  public void stateTypeTest() {
    assertEquals(new BattleState(null).getType(), StateType.BATTLE);
  }

}