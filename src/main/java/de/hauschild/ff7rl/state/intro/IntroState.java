/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.state.intro;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.googlecode.lanterna.screen.Screen;

import de.hauschild.ff7rl.Context;
import de.hauschild.ff7rl.assets.Resources;
import de.hauschild.ff7rl.assets.Sound;
import de.hauschild.ff7rl.assets.Sounds;
import de.hauschild.ff7rl.input.Input;
import de.hauschild.ff7rl.state.AbstractState;
import de.hauschild.ff7rl.state.StateHandler;
import de.hauschild.ff7rl.state.StateType;
import de.hauschild.ff7rl.ui.ScreenMenu;

/**
 * @author Klaus Hauschild
 */
public class IntroState extends AbstractState {

    private static final Logger          LOGGER = LoggerFactory.getLogger(IntroState.class);

    private final List<String>           title  = Lists.newArrayList();
    private Sound                        introSound;
    private ScreenMenu<ScreenMenu.Entry> mainMenu;

    public IntroState(final Context context) {
        super(StateType.INTRO, context);
    }

    @Override
    public void enter() {
        super.enter();
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(Resources
                .getInputStream("assets/misc", "intro.txt").openInputStream()), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                title.add(line);
            }
        } catch (final Exception exception) {
            throw new RuntimeException("Unable to read title graphic.", exception);
        }
        introSound = Sounds.getSound("1-01 Prelude.mp3");
        introSound.start();
        final ScreenMenu.Entry newGameEntry = new ScreenMenu.Entry("New Game");
        final ScreenMenu.Entry continueEntry = new ScreenMenu.Entry("Continue", false);
        final ScreenMenu.Entry bossRushEntry = new ScreenMenu.Entry("Boss Rush");
        mainMenu = new ScreenMenu<>(Lists.newArrayList(newGameEntry, continueEntry, bossRushEntry), 35, 50);
    }

    @Override
    public void display(final Screen screen) {
        int i = 10;
        for (final String line : title) {
            screen.newTextGraphics().putString(7, i, line);
            i++;
        }
        screen.newTextGraphics().putString(96, 47, "(c) 2015 Klaus Hauschild");
        mainMenu.display(screen);
    }

    @Override
    public void input(final Input input, final StateHandler stateHandler) {
        switch (input) {
            case DOWN:
                mainMenu.next();
                break;
            case UP:
                mainMenu.previous();
                break;
            case ACCEPT:
                LOGGER.info("[{}] selected.", mainMenu.select());
                stateHandler.nextState(StateType.BATTLE);
                break;
        }
    }

    @Override
    public void leave() {
        super.leave();
        introSound.stop();
    }

}
