/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.context;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hauschild.ff7rl.context.ContextConstants.General;
import de.hauschild.ff7rl.context.ContextConstants.Room;
import de.hauschild.ff7rl.state.StateType;

/**
 * @author Klaus Hauschild
 */
public class Context {

    private static final Logger LOGGER = LoggerFactory.getLogger(Context.class);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Map<String, Object> data = Maps.newHashMap();

    public static Context createStartContext() {
        final Context context = new Context();
        context.set(Room.ROOM, "assault_on_mako_reactor_no_1/room_a");
        return context;
    }

    public static Context fromJson(final String json) {
        final Context context = new Context();
        context.data.putAll(GSON.fromJson(json, Map.class));
        return context;
    }

    public static String toJson(final Context context) {
        return GSON.toJson(context.data);
    }

    private Context() {
    }

    public String getRoom() {
        return (String) get(Room.ROOM);
    }

    public int getSteps() {
        return (int) get(General.STEPS, 0);
    }

    public void incrementSteps() {
        final int steps = getSteps();
        set(General.STEPS, steps + 1);
    }

    protected Object get(final String key) {
        return data.get(key);
    }

    protected Object get(final String key, final Object defaultValue) {
        final Object value = data.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    protected void set(final String key, final Object value) {
        final Object oldValue = get(key);
        LOGGER.debug("set [{}]: [{}] -> [{}]", key, oldValue, value);
        data.put(key, value);
    }

    public void setLastState(final StateType type) {
        set(General.LAST_STATE, type);
    }

    public StateType getLastState() {
        return (StateType) get(General.LAST_STATE);
    }

    public String getRegion() {
        return (String) get(General.REGION);
    }

    public int getGil() {
        return (int) get(General.GIL, 0);
    }

}
