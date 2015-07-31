/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl;

import ch.qos.logback.classic.Level;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import de.hauschild.ff7rl.assets.Sounds;
import de.hauschild.ff7rl.debug.Console;
import de.hauschild.ff7rl.input.Input;
import de.hauschild.ff7rl.input.InputMapping;
import de.hauschild.ff7rl.state.State;
import de.hauschild.ff7rl.state.StateHandler;
import de.hauschild.ff7rl.state.StateType;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * @author Klaus Hauschild
 */
class Main {

  private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private Main() {
  }

  public static void main(final String[] args) {
    processArguments(args);
    final DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
    terminalFactory.setSwingTerminalFrameTitle(String.format("Final Fantasy 7 - Roguelike (%s)", Version.get()));
    terminalFactory.setInitialTerminalSize(new TerminalSize(120, 48));
    final SwingTerminalFrame terminal = getTerminal(terminalFactory);
    terminal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    terminal.setResizable(false);
    terminal.setLocationRelativeTo(null);
    try {
      mainLoop(new TerminalScreen(terminal));
    } catch (final Exception exception) {
      LOGGER.error("Unexpected error.", exception);
      System.exit(1);
    }
  }

  private static void processArguments(final String[] args) {
    final OptionParser optionParser = new OptionParser();
    final String muteOption = "mute";
    final String debugOption = "debug";
    optionParser.accepts(muteOption, "Mutes the hole game.");
    optionParser.accepts(debugOption, "Active the debug mode (logging, debug console)");
    final OptionSet options = optionParser.parse(args);
    if (options.has(muteOption)) {
      Sounds.mute();
    }
    if (options.has(debugOption)) {
      Console.enableDebugConsole();
      setLogLevel(Level.DEBUG);
    } else {
      setLogLevel(Level.OFF);
    }
  }

  private static void setLogLevel(final Level level) {
    ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Main.class.getPackage().getName())).setLevel(level);
  }

  private static SwingTerminalFrame getTerminal(DefaultTerminalFactory terminalFactory) {
    try {
      return (SwingTerminalFrame) terminalFactory.createTerminal();
    } catch (final Exception exception) {
      throw new RuntimeException("Unable to initialize terminal.", exception);
    }
  }

  private static void mainLoop(final Screen screen) throws Exception {
    // initialize screen
    screen.startScreen();
    screen.setCursorPosition(null);
    // initialize key mapping
    final InputMapping inputMapping = new InputMapping();
    // initialize context
    final Context context = new Context();
    // initialize state
    State state = StateType.INTRO.getState(context);
    state.enter();
    boolean skipNextInput = false;
    while (true) {
      // display state
      screen.clear();
      state.display(screen);
      screen.refresh();
      // update state
      Input input;
      if (skipNextInput) {
        skipNextInput = false;
        input = null;
      } else {
        input = inputMapping.map(screen.readInput());
      }
      if (input == Input.DEBUG_CONSOLE) {
        Console.openDebugConsole(state);
        input = inputMapping.map(screen.readInput());
      }
      final StateHandler stateHandler = new StateHandler();
      state.input(input, stateHandler);
      final StateType nextStateType = stateHandler.getNextStateType();
      if (nextStateType != null) {
        state.leave();
        state = nextStateType.getState(context);
        state.enter();
        Console.rebind(state);
      }
      final Integer skipNextInputMillis = stateHandler.getMillis();
      if (skipNextInputMillis != null) {
        skipNextInput = true;
        Thread.sleep(skipNextInputMillis);
      }
    }
  }

}
