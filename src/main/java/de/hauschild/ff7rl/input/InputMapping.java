/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

/**
 * @author Klaus Hauschild
 */
public class InputMapping {

    private static final File                  INPUT_MAPPING_FILE = new File("input.mapping");
    private static final Map<KeyStroke, Input> DEFAULT_MAPPINGS   = ImmutableMap
                                                                          .<KeyStroke, Input>builder()
                                                                          .put(new KeyStroke(KeyType.ArrowDown), Input.DOWN)
                                                                          .put(new KeyStroke(KeyType.ArrowUp), Input.UP)
                                                                          .put(new KeyStroke(KeyType.ArrowRight), Input.RIGHT)
                                                                          .put(new KeyStroke(KeyType.ArrowLeft), Input.LEFT)
                                                                          .put(new KeyStroke(KeyType.Enter), Input.ACCEPT)
                                                                          .put(new KeyStroke(KeyType.Escape), Input.ABORT)
                                                                          .put(new KeyStroke(KeyType.Backspace), Input.ABORT)
                                                                          .put(new KeyStroke(' ', false, false), Input.MENU)
                                                                          .put(new KeyStroke(KeyType.ReverseTab),
                                                                                  Input.DEBUG_CONSOLE).build();

    private final Map<KeyStroke, Input>        keyMappings;

    public InputMapping() {
        final Map<KeyStroke, Input> userInputMapping = readInputMapping();
        if (userInputMapping != null) {
            keyMappings = Maps.newHashMap(userInputMapping);
        } else {
            keyMappings = Maps.newHashMap(DEFAULT_MAPPINGS);
            writeInputMapping();
        }
    }

    public Input map(final KeyStroke keyStroke) {
        return keyMappings.get(keyStroke);
    }

    private Map<KeyStroke, Input> readInputMapping() {
        if (!INPUT_MAPPING_FILE.exists()) {
            return null;
        }
        final Properties inputMapping = new Properties();
        try (final BufferedReader reader = Files.newReader(INPUT_MAPPING_FILE, StandardCharsets.UTF_8)) {
            inputMapping.load(reader);
            final Map<KeyStroke, Input> result = Maps.newHashMap();
            for (final Input input : Input.values()) {
                final String keyStrokes = inputMapping.getProperty(input.name());
                if (Strings.isNullOrEmpty(keyStrokes)) {
                    throw new IllegalStateException(String.format("No key binding for input [%s].", input));
                }
                for (final String keyStroke : Splitter.on(",").split(keyStrokes)) {
                    result.put(KeyStrokes.fromString(keyStroke), input);
                }
            }
            return result;
        } catch (final Exception exception) {
            throw new RuntimeException("Unable to read input mapping file.", exception);
        }
    }

    private void writeInputMapping() {
        final Properties inputMappings = new Properties();
        for (final Input input : Input.values()) {
            final Iterable<Entry<KeyStroke, Input>> keyStrokes = Iterables.filter(keyMappings.entrySet(),
                    entry -> input == entry.getValue());
            inputMappings.put(input.name(),
                    Joiner.on(",").join(Iterables.transform(keyStrokes, keyStroke -> KeyStrokes.toString(keyStroke.getKey()))));
        }
        try (final BufferedWriter writer = Files.newWriter(INPUT_MAPPING_FILE, Charsets.UTF_8)) {
            final String comments = " Use this file to customize the your mappings.\n"
                    + " One command can be mapped to multiple input. Multiple inputs has to be separated by comma (,).\n"
                    + " For a key combination with [Ctrl] prefix the input with \"Ctrl+\". For a key combination with [Alt] prefix the input with \"Alt+\".\n"
                    + " Each combination of \"Ctrl+\" and \"Alt+\" is possible.\n"
                    + " An input is a printable character in lowercase or uppercase or one of the following special keys (note that not all keyboards support all special keys):\n"
                    + " "
                    + Joiner.on(",").join(
                            Iterables.transform(
                                    Iterables.filter(Arrays.asList(KeyType.values()), keyType -> keyType != KeyType.Character
                                            && keyType != KeyType.Unknown && keyType != KeyType.CursorLocation
                                            && keyType != KeyType.EOF), Enum::name)) + "," + KeyStrokes.SPACE + "\n" + "";
            inputMappings.store(writer, comments);
        } catch (final Exception exception) {
            throw new RuntimeException("Unable to write input mapping file.", exception);
        }
    }

}
