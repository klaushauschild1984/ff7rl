/* Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2014
 */
package de.hauschild.ff7rl.state;

import com.googlecode.lanterna.screen.Screen;
import de.hauschild.ff7rl.input.Input;

/**
 * @author Klaus Hauschild
 */
public interface State {

  StateType getType();

  void enter();

  void display(Screen screen);

  void input(Input input, StateHandler stateHandler);

  void leave();

}
