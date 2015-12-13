/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.state.map;

import com.googlecode.lanterna.screen.Screen;

import de.hauschild.ff7rl.context.Context;
import de.hauschild.ff7rl.assets.images.Image;
import de.hauschild.ff7rl.assets.images.Images;
import de.hauschild.ff7rl.assets.rooms.Room;
import de.hauschild.ff7rl.assets.rooms.Rooms;
import de.hauschild.ff7rl.input.Input;
import de.hauschild.ff7rl.state.AbstractState;
import de.hauschild.ff7rl.state.StateHandler;
import de.hauschild.ff7rl.state.StateType;

/**
 * @author Klaus Hauschild
 */
// TODO this state has to be an abstraction for INTERIOR_MAP and WORLD_MAP
public class MapState extends AbstractState {

    private final Image cloudImage;
    private final Room  room;

    private int         x = 12;
    private int         y = 12;

    public MapState(final Context context) {
        super(StateType.INTERIOR_MAP, context);
        cloudImage = Images.getImage("map/cloud");
        room = Rooms.getRoom(context.getRoom());
    }

    @Override
    public void enter() {
        super.enter();
        room.enter(getContext());
    }

    @Override
    public void display(final Screen screen) {
        room.display(screen, room.getTop(), room.getLeft());
        cloudImage.display(screen, y, x);
    }

    @Override
    public void leave() {
        super.leave();
        room.leave(getContext());
    }

    @Override
    public void input(final Input input, final StateHandler stateHandler) {
        if (input == null) {
            return;
        }
        switch (input) {
            case UP:
                if (room.isBlocked(x, y - 1)) {
                    break;
                }
                getContext().incrementSteps();
                y--;
                break;
            case DOWN:
                if (room.isBlocked(x, y + 1)) {
                    break;
                }
                getContext().incrementSteps();
                y++;
                break;
            case LEFT:
                if (room.isBlocked(x - 1, y)) {
                    break;
                }
                getContext().incrementSteps();
                x--;
                break;
            case RIGHT:
                if (room.isBlocked(x + 1, y)) {
                    break;
                }
                getContext().incrementSteps();
                x++;
                break;
            case MENU:
                stateHandler.nextState(StateType.MENU);
                break;
        }
    }

}
