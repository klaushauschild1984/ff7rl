/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.state.intro;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import de.hauschild.ff7rl.context.KernelContext;
import de.hauschild.ff7rl.context.SaveStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.googlecode.lanterna.screen.Screen;

import de.hauschild.ff7rl.assets.Resources;
import de.hauschild.ff7rl.assets.sounds.Sound;
import de.hauschild.ff7rl.assets.sounds.Sounds;
import de.hauschild.ff7rl.context.Context;
import de.hauschild.ff7rl.input.Input;
import de.hauschild.ff7rl.state.AbstractState;
import de.hauschild.ff7rl.state.StateHandler;
import de.hauschild.ff7rl.state.StateType;
import de.hauschild.ff7rl.ui.ScreenMenu;
import de.hauschild.ff7rl.ui.ScreenMenu.Entry;

/**
 * @author Klaus Hauschild
 */
public class IntroState extends AbstractState {

    private static final Logger LOGGER         = LoggerFactory.getLogger(IntroState.class);

    private final List<String>  title          = Lists.newArrayList();
    private final Entry         newGameEntry   = new Entry("New Game");
    private final Entry         continueEntry  = new Entry("Continue", false);
    private final Entry         soundTestEntry = new Entry("Sound test");
    private Sound               introSound;
    private ScreenMenu<Entry>   mainMenu;

    public IntroState(final Context context) {
        super(StateType.INTRO, context);
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(
                new BufferedInputStream(Resources.getInputStream("assets/misc", "intro.txt").openInputStream()),
                StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                title.add(line);
            }
        } catch (final Exception exception) {
            throw new RuntimeException("Unable to read title graphic.", exception);
        }
        if (!SaveStates.getPresentSaveSlots().isEmpty()) {
            continueEntry.setEnabled(true);
        }
        introSound = Sounds.getSound("1-01 Prelude.mp3");
    }

    @Override
    public void enter() {
        super.enter();
        introSound.start();
        mainMenu = new ScreenMenu<>(Lists.newArrayList(newGameEntry, continueEntry, soundTestEntry), 35, 50);
    }

    @Override
    public void display(final Screen screen) {
        int i = 10;
        for (final String line : title) {
            screen.newTextGraphics().putString(7, i, line);
            i++;
        }
        screen.newTextGraphics().putString(98, 47, "© 2015 Klaus Hauschild");
        mainMenu.display(screen);
    }

    @Override
    public void input(final Input input, final StateHandler stateHandler) {
        if (input == null) {
            return;
        }
        switch (input) {
            case DOWN:
                mainMenu.next();
                break;
            case UP:
                mainMenu.previous();
                break;
            case ACCEPT:
                final Entry selected = mainMenu.select();
                LOGGER.debug("[{}] selected.", selected);
                if (selected.equals(newGameEntry)) {
                    stateHandler.nextState(StateType.INTERIOR_MAP);
                } else if (selected.equals(continueEntry)) {
                    // TODO change to slot selection
                    SaveStates.restoreSlot(getContext(), SaveStates.getPresentSaveSlots().get(0));
                    stateHandler.nextState(KernelContext.getLastState(getContext()));
                } else if (selected.equals(soundTestEntry)) {
                    stateHandler.nextState(StateType.SOUND_TEST);
                }
                break;
        }
    }

    @Override
    public void leave() {
        super.leave();
        introSound.stop();
    }

}
