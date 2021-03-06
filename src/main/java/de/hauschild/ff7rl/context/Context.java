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
import com.googlecode.lanterna.TextColor.RGB;

/**
 * @author Klaus Hauschild
 */
public class Context {

    private static final Logger       LOGGER = LoggerFactory.getLogger(Context.class);
    private static final Gson         GSON   = new GsonBuilder().setPrettyPrinting().create();

    private final Map<String, Object> data   = Maps.newHashMap();

    private Context() {
    }

    public static Context createStartContext() {
        final Context context = new Context();
        RoomContext.setRoomName(context, "assault_on_mako_reactor_no_1/room_a");
        KernelContext.setColors(context, new RGB(0, 0, 128));
        return context;
    }

    static void fromJson(final Context context, final String json) {
        context.data.clear();
        context.data.putAll(GSON.fromJson(json, Map.class));
    }

    static String toJson(final Context context) {
        return GSON.toJson(context.data);
    }

    public <T> T get(final String key) {
        return (T) data.get(key);
    }

    public <T> T get(final String key, final T defaultValue) {
        final Object value = data.get(key);
        if (value == null) {
            return defaultValue;
        }
        if (defaultValue != null && defaultValue instanceof Integer && value instanceof Double) {
            set(key, ((Double) value).intValue());
            return get(key, defaultValue);
        }
        return (T) value;
    }

    public void set(final String key, final Object value) {
        final Object oldValue = get(key);
        LOGGER.debug("set [{}]: [{}] -> [{}]", key, oldValue, value);
        data.put(key, value);
    }

}
