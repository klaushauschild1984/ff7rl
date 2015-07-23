/* Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2014
 */
package de.hauschild.ff7rl.state.intro;

import com.google.common.collect.Lists;
import com.googlecode.lanterna.screen.Screen;
import de.hauschild.ff7rl.assets.Resources;
import de.hauschild.ff7rl.assets.Sound;
import de.hauschild.ff7rl.assets.Sounds;
import de.hauschild.ff7rl.input.Input;
import de.hauschild.ff7rl.state.AbstractState;
import de.hauschild.ff7rl.state.StateHandler;
import de.hauschild.ff7rl.state.StateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Klaus Hauschild
 */
public class IntroState extends AbstractState {

  private static final Logger LOGGER = LoggerFactory.getLogger(IntroState.class);

  private enum MainMenuEntry {

    NEW_GAME,

    CONTINUE,

  }

  private final List<String> title = Lists.newArrayList();

  private Sound introSound;
  private MainMenuEntry mainMenuEntry = MainMenuEntry.NEW_GAME;

  public IntroState() {
    super(StateType.INTRO);
  }

  @Override
  public void enter() {
    super.enter();
    try {
      final BufferedReader reader = new BufferedReader(new InputStreamReader(
          new BufferedInputStream(Resources.getInputStream("assets/misc", "intro.txt").openInputStream())));
      String line;
      while ((line = reader.readLine()) != null) {
        title.add(line);
      }
    } catch (final Exception exception) {
      throw new RuntimeException("Unable to render intro.", exception);
    }
    introSound = Sounds.getSound("Prelude.mp3");
    introSound.start();
  }

  @Override
  public void display(final Screen screen) {
    int i = 10;
    for (final String line : title) {
      screen.newTextGraphics().putString(7, i, line);
      i++;
    }
    screen.newTextGraphics().putString(96, 47, "(c) 2015 Klaus Hauschild");
    screen.newTextGraphics().putString(50, 35 + mainMenuEntry.ordinal() * 2, "->");
    screen.newTextGraphics().putString(53, 35, "New Game");
    screen.newTextGraphics().putString(53, 37, "Continue");
  }

  @Override
  public void input(final Input input, final StateHandler stateHandler) {
    switch (input) {
      case DOWN:
        mainMenuEntry = fromOrdinal(mainMenuEntry.ordinal() + 1);
        break;
      case UP:
        mainMenuEntry = fromOrdinal(mainMenuEntry.ordinal() - 1);
        break;
      case ACCEPT:
        LOGGER.info("[{}] selected.", mainMenuEntry);
        stateHandler.nextState(StateType.BATTLE);
    }
  }

  @Override
  public void leave() {
    super.leave();
    introSound.stop();
  }

  private MainMenuEntry fromOrdinal(final int ordinal) {
    if (ordinal == -1) {
      return MainMenuEntry.values()[MainMenuEntry.values().length - 1];
    }
    for (final MainMenuEntry entry : MainMenuEntry.values()) {
      if (entry.ordinal() == ordinal % MainMenuEntry.values().length) {
        return entry;
      }
    }
    throw new IllegalStateException();
  }

}
