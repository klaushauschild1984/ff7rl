/* Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2014
 */
package de.hauschild.ff7rl.state;

import com.google.common.collect.Maps;
import de.hauschild.ff7rl.state.battle.BattleState;
import de.hauschild.ff7rl.state.intro.IntroState;

import java.util.Map;

/**
 * @author Klaus Hauschild
 */
public enum StateType {

  INTRO(IntroState.class),

  CHARACTER_CREATION(null),

  MENU(null),

  WORLD_MAP(null),

  INTERIOR_MAP(null),

  BATTLE(BattleState.class),

  ;

  private static final Map<StateType, State> STATES = Maps.newEnumMap(StateType.class);

  private final Class<? extends State> stateClass;

  StateType(final Class<? extends State> stateClass) {
    this.stateClass = stateClass;
  }

  public State getState() {
    try {
      State state = STATES.get(this);
      if (state == null) {
        state = stateClass.newInstance();
        STATES.put(this, state);
      }
      return state;
    } catch (final Exception exception) {
      throw new RuntimeException(String.format("Unable to instantiate state %s", this), exception);
    }
  }

}
