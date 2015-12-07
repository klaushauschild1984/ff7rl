/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.state.map;

import com.googlecode.lanterna.screen.Screen;

import de.hauschild.ff7rl.Context;
import de.hauschild.ff7rl.assets.images.Images;
import de.hauschild.ff7rl.assets.images.ScreenImage;
import de.hauschild.ff7rl.input.Input;
import de.hauschild.ff7rl.state.AbstractState;
import de.hauschild.ff7rl.state.StateHandler;
import de.hauschild.ff7rl.state.StateType;

/**
 * @author Klaus Hauschild
 */
// TODO this state has to be an abstraction for INTERIOR_MAP and WORLD_MAP
public class MapState extends AbstractState {

    private final ScreenImage cloudImage;

    private int               x = 10;
    private int               y = 10;

    public MapState(final Context context) {
        super(StateType.INTERIOR_MAP, context);
        cloudImage = Images.getImage("map/cloud/cloud");
    }

    @Override
    public void display(final Screen screen) {
        cloudImage.display(screen, y, x);
    }

    @Override
    public void input(final Input input, final StateHandler stateHandler) {
        if (input == null) {
            return;
        }
        switch (input) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }
    }
}
