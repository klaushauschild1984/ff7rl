/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.state.soundtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.lanterna.screen.Screen;

import de.hauschild.ff7rl.Context;
import de.hauschild.ff7rl.assets.sounds.Sounds;
import de.hauschild.ff7rl.input.Input;
import de.hauschild.ff7rl.state.AbstractState;
import de.hauschild.ff7rl.state.StateHandler;
import de.hauschild.ff7rl.state.StateType;

/**
 * @author Klaus Hauschild
 */
public class SoundTestState extends AbstractState {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundTestState.class);

    public SoundTestState(final Context context) {
        super(StateType.SOUND_TEST, context);
        Sounds.getAllSounds().forEach(sound -> LOGGER.debug(sound));
    }

    @Override
    public void display(final Screen screen) {
    }

    @Override
    public void input(final Input input, final StateHandler stateHandler) {
        switch (input) {
            case ABORT:
                stateHandler.nextState(StateType.INTRO);
                break;
        }
    }

}
