/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl;

import ch.qos.logback.classic.Level;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import de.hauschild.ff7rl.assets.sounds.Sounds;
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
import java.io.IOException;

/**
 * @author Klaus Hauschild
 */
enum Main {

    ;

    private static final Logger LOGGER       = LoggerFactory.getLogger(Main.class);

    private static StateType    initialState = StateType.INTRO;

    public static void main(final String[] args) {
        processArguments(args);
        final DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setSwingTerminalFrameTitle(String.format("Final Fantasy 7 roguelike (%s)", Version.get()));
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
        optionParser.accepts(muteOption, "Mutes the hole game.");
        final String debugOption = "debug";
        optionParser.accepts(debugOption, "Active the debug mode (logging, debug console, debug options)");
        final String initialStateOption = "initialState";
        optionParser.accepts(initialStateOption, "[only evaluated with --debug] specifies the initial game state")
                .withRequiredArg();
        final OptionSet options = optionParser.parse(args);
        if (options.has(muteOption)) {
            Sounds.mute();
        }
        if (options.has(debugOption)) {
            Console.enableDebugConsole();
            setLogLevel(Level.DEBUG);
            if (options.has(initialStateOption) && options.hasArgument(initialStateOption)) {
                initialState = StateType.valueOf((String) options.valueOf(initialStateOption));
            }
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
        final Context context = Context.createStartContext();
        // initialize state
        State state = initialState.getState(context);
        state.enter();
        boolean skipNextInput = false;
        while (true) {
            // update display
            displayState(screen, state);
            // get next input
            Input input = getInput(screen, inputMapping, state, skipNextInput);
            // update state
            final StateHandler stateHandler = new StateHandler();
            state.input(input, stateHandler);
            // handle next state
            state = handleNextState(context, state, stateHandler);
            // sleep if previous state requires it
            skipNextInput = sleepIfNecessary(stateHandler);
        }
    }

    private static State handleNextState(Context context, State state, StateHandler stateHandler) {
        final StateType nextStateType = stateHandler.getNextStateType();
        if (nextStateType != null) {
            state.leave();
            state = nextStateType.getState(context);
            state.enter();
            Console.rebind(state);
        }
        return state;
    }

    private static Input getInput(Screen screen, InputMapping inputMapping, State state, boolean skipNextInput)
            throws IOException {
        if (skipNextInput) {
            return null;
        }
        Input input = inputMapping.map(screen.readInput());
        if (input == Input.DEBUG_CONSOLE) {
            Console.openDebugConsole(state);
            input = inputMapping.map(screen.readInput());
        }
        return input;
    }

    private static void displayState(Screen screen, State state) throws IOException {
        screen.clear();
        state.display(screen);
        screen.refresh();
    }

    private static boolean sleepIfNecessary(final StateHandler stateHandler) {
        final Integer skipNextInputMillis = stateHandler.getMillis();
        if (skipNextInputMillis != null) {
            Threads.sleep(skipNextInputMillis);
            return true;
        }
        return false;
    }

}
