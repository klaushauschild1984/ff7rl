/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.context;

import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.common.base.Splitter;
import de.hauschild.ff7rl.Actor;

/**
 * @author Klaus Hauschild
 */
public enum RoomContext {

    ;

    static final String ACTIVE_ACTOR = "ff7rl.context.room.active-actor";
    static final String ROOM_NAME    = "ff7rl.context.room.name";
    static final String ACTOR_PLACE  = "ff7rl.context.room.actor-place.";

    public static String getRoomName(final Context context) {
        return (String) context.get(ROOM_NAME);
    }

    public static String getRegion(final Context context) {
        return (String) context.get(KernelContext.REGION);
    }

    public static void setRegion(final Context context, final String region) {
        context.set(KernelContext.REGION, region);
    }

    public static void placeActor(final Context context, final Actor actor, final int top, final int left) {
        context.set(getActorPlaceKey(actor), String.format("%s,%s", top, left));
    }

    public static void activeActor(final Context context, final Actor actor) {
        context.set(ACTIVE_ACTOR, actor);
    }

    public static Actor activeActor(final Context context) {
        return context.get(ACTIVE_ACTOR);
    }

    public static Entry<Integer, Integer> actorPlace(final Context context, final Actor actor) {
        final String placeValue = context.get(getActorPlaceKey(actor));
        final Iterator<String> iterator = Splitter.on(",").split(placeValue).iterator();
        return new SimpleEntry<>(Integer.valueOf(iterator.next()), Integer.valueOf(iterator.next()));
    }

    public static boolean actorPresent(final Context context, final Actor actor) {
        return context.get(getActorPlaceKey(actor)) != null;
    }

    private static String getActorPlaceKey(final Actor actor) {
        return ACTOR_PLACE + actor.name();
    }
}
