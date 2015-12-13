/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.state.menu;

import com.google.common.base.Strings;
import com.googlecode.lanterna.TextColor.RGB;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import de.hauschild.ff7rl.context.Context;
import de.hauschild.ff7rl.input.Input;
import de.hauschild.ff7rl.state.AbstractState;
import de.hauschild.ff7rl.state.StateHandler;
import de.hauschild.ff7rl.state.StateType;
import de.hauschild.ff7rl.ui.ScreenBorder;

/**
 * @author Klaus Hauschild
 */
public class MenuState extends AbstractState {

    public MenuState(final Context context) {
        super(StateType.MENU, context);
    }

    @Override
    public void display(final Screen screen) {
        final TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setBackgroundColor(new RGB(0, 0, 128));

        // region
        new ScreenBorder(60, 3).display(screen, 45, 60);
        textGraphics.putString(61, 46, getContext().getRegion());

        // gil and steps
        new ScreenBorder(30, 6).display(screen, 38, 90);
        textGraphics.putString(91, 39, "Steps");
        textGraphics.putString(91, 40, Strings.padStart(String.valueOf(getContext().getSteps()), 28, ' '));
        textGraphics.putString(91, 41, "Gil");
        textGraphics.putString(91, 42, Strings.padStart(String.valueOf(getContext().getGil()), 28, ' '));
    }

    @Override
    public void input(final Input input, final StateHandler stateHandler) {
        if (input == null) {
            return;
        }
        switch (input) {
            case MENU:
            case ABORT:
                stateHandler.nextState(getContext().getLastState());
        }
    }

}
