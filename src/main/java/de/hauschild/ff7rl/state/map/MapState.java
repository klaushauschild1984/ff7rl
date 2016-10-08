/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.state.map;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map.Entry;

import com.googlecode.lanterna.screen.Screen;

import de.hauschild.ff7rl.Actor;
import de.hauschild.ff7rl.assets.images.Image;
import de.hauschild.ff7rl.assets.rooms.Room;
import de.hauschild.ff7rl.assets.rooms.Rooms;
import de.hauschild.ff7rl.context.Context;
import de.hauschild.ff7rl.context.KernelContext;
import de.hauschild.ff7rl.context.RoomContext;
import de.hauschild.ff7rl.input.Input;
import de.hauschild.ff7rl.state.AbstractState;
import de.hauschild.ff7rl.state.StateHandler;
import de.hauschild.ff7rl.state.StateType;

/**
 * @author Klaus Hauschild
 */
// TODO this state has to be an abstraction for INTERIOR_MAP and WORLD_MAP
public class MapState extends AbstractState {

    private final Room room;

    public MapState(final Context context) {
        super(StateType.INTERIOR_MAP, context);
        room = Rooms.getRoom(RoomContext.getRoomName(getContext()));
        room.getRoomScript().initialize(context);
    }

    @Override
    public void enter() {
        super.enter();
        room.getRoomScript().enter();
    }

    @Override
    public void display(final Screen screen) {
        // display room
        room.getRoomImage().display(screen, room.getRoomScript().getTop(), room.getRoomScript().getLeft());
        // display actors
        Arrays.stream(Actor.values()).filter(actor -> RoomContext.actorPresent(getContext(), actor)).forEach(actor -> {
            final Entry<Integer, Integer> actorPlace = RoomContext.actorPlace(getContext(), actor);
            final Image actorImage = actor.getImage();
            actorImage.display(screen, actorPlace.getKey(), actorPlace.getValue());
        });
    }

    @Override
    public void input(final Input input, final StateHandler stateHandler) {
        if (input == null) {
            final int sleepMillis = room.getRoomScript().update();
            stateHandler.skipNextInput(sleepMillis);
            return;
        }
        final Actor actor = RoomContext.activeActor(getContext());
        if (actor == null) {
            return;
        }
        Entry<Integer, Integer> actorPlace = RoomContext.actorPlace(getContext(), actor);
        int x = 0;
        int y = 0;
        switch (input) {
            case UP:
                y = -1;
                break;
            case DOWN:
                y = 1;
                break;
            case RIGHT:
                x = 1;
                break;
            case LEFT:
                x = -1;
                break;
            case MENU:
                stateHandler.nextState(StateType.MENU);
                break;
        }
        if (x != 0 || y != 0) {
            // handle movement
            actorPlace = new SimpleEntry<>(actorPlace.getKey() + y, actorPlace.getValue() + x);
            if (!isBlocked(actorPlace)) {
                RoomContext.placeActor(getContext(), actor, actorPlace.getKey(), actorPlace.getValue());
                KernelContext.incrementSteps(getContext());
            }
        }
    }

    @Override
    public void leave() {
        super.leave();
        room.getRoomScript().leave();
    }

    // TODO maybe x and y should be transformed into rooms space
    public boolean isBlocked(final Entry<Integer, Integer> place) {
        final int x = place.getValue() - room.getRoomScript().getLeft();
        final int y = place.getKey() - room.getRoomScript().getTop();
        return room.getWalls()[x][y];
    }

}
