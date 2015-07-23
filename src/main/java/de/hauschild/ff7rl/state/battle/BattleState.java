/* Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2014
 */
package de.hauschild.ff7rl.state.battle;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import de.hauschild.ff7rl.assets.Sound;
import de.hauschild.ff7rl.assets.Sounds;
import de.hauschild.ff7rl.input.Input;
import de.hauschild.ff7rl.state.AbstractState;
import de.hauschild.ff7rl.state.ScreenUtils;
import de.hauschild.ff7rl.state.StateHandler;
import de.hauschild.ff7rl.state.StateType;

/**
 * @author Klaus Hauschild
 */
public class BattleState extends AbstractState {

  private Sound fightingSound;

  public BattleState() {
    super(StateType.BATTLE);
  }

  @Override
  public void enter() {
    super.enter();
    fightingSound = Sounds.getSound("Fighting.mp3");
    fightingSound.loop();
  }

  @Override
  public void display(final Screen screen) {
    ScreenUtils.renderBox(screen.newTextGraphics(), 30, 0, 60, 8);

    screen.newTextGraphics().putString(2, 31, "Cloud");
    screen.newTextGraphics().putString(20, 31, "6666/9999");
    screen.newTextGraphics().setForegroundColor(new TextColor.RGB(255, 0, 0))
        .putString(26, 32, "---");
    screen.newTextGraphics().setForegroundColor(new TextColor.RGB(0, 100, 255))
        .putString(20, 32, "------");
  }

  @Override
  public void input(final Input input, StateHandler stateHandler) {
  }

  @Override
  public void leave() {
    super.leave();
    fightingSound.stop();
  }
}
