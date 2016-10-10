/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package de.hauschild.ff7rl.context;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * @author Klaus Hauschild
 */
public class SaveStates {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveStates.class);

    public static void writeSlot(final Context context, final int slot) {
        LOGGER.debug(String.format("Save current game to slot %s.", slot));
        final String json = Context.toJson(context);
        try {
            Files.write(json, slotFile(slot), Charsets.UTF_8);
        } catch (final IOException exception) {
            LOGGER.error("Unable to write save file!", exception);
        }
    }

    public static void restoreSlot(final Context context, final int slot) {
        LOGGER.debug(String.format("Restore current game from slot %s.", slot));
        try {
            final String json = Files.readLines(slotFile(slot), Charsets.UTF_8).stream().reduce("", (a, b) -> a + b);
            Context.fromJson(context, json);
        } catch (final IOException exception) {
            LOGGER.error("Unable to read save file!", exception);
        }
    }

    public static List<Integer> getPresentSaveSlots() {
        return Stream.iterate(1, i -> 1 + 1).limit(15).filter(slot -> slotFile(slot).exists()).collect(Collectors.toList());
    }

    private static File slotFile(final int slot) {
        return new File(String.format("ff7rl.slot%s", slot));
    }

}
