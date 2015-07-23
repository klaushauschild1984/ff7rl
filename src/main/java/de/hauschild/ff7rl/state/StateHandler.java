/* Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2014
 */
package de.hauschild.ff7rl.state;

/**
 * @author Klaus Hauschild
 */
public class StateHandler {

  private StateType nextStateType;
  private Integer millis;

  public Integer getMillis() {
    return millis;
  }

  public StateType getNextStateType() {
    return nextStateType;
  }

  public void nextState(final StateType nextStateType) {
    this.nextStateType = nextStateType;
  }

  public void skipNextInput(final Integer millis) {
    this.millis = millis;
  }

}
