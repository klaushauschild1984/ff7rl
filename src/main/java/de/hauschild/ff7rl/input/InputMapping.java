/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.input;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.BufferedWriter;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * @author Klaus Hauschild
 */
public class InputMapping {

  private static final File INPUT_MAPPING_FILE = new File("input.mapping");
  private static final Map<KeyStroke, Input> DEFAULT_MAPPINGS = ImmutableMap.<KeyStroke, Input>builder()
      .put(new KeyStroke(KeyType.ArrowDown), Input.DOWN)
      .put(new KeyStroke(KeyType.ArrowUp), Input.UP)
      .put(new KeyStroke(KeyType.ArrowRight), Input.RIGHT)
      .put(new KeyStroke(KeyType.ArrowLeft), Input.LEFT)
      .put(new KeyStroke(KeyType.Enter), Input.ACCEPT)
      .put(new KeyStroke(KeyType.Escape), Input.ABORT)
      .put(new KeyStroke(KeyType.Backspace), Input.ABORT)
      .put(new KeyStroke(' ', false, false), Input.MENU)
      .build();

  private final Map<KeyStroke, Input> keyMappings;

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
    // TODO implement me
    return null;
  }

  private void writeInputMapping() {
    final Properties inputMappings = new Properties();
    for (final Input input : Input.values()) {
      final Iterable<Entry<KeyStroke, Input>> keyStrokes = Iterables.filter(keyMappings.entrySet(), entry -> input == entry.getValue());
      inputMappings.put(input.name(),
          Joiner.on(",").join(Iterables.transform(keyStrokes, keyStroke -> KeyStrokes.toString(keyStroke.getKey()))));
    }
    try (final BufferedWriter writer = Files.newWriter(INPUT_MAPPING_FILE, Charsets.UTF_8)) {
      // TODO more descriptive comment which keys are available
      final String comments =
          "Use this file to customize the your mappings.\n" +
          "One command can be mapped to multiple input. Multiple inputs has to be separated by comma (,).\n" +
          "For a key combination with [Ctrl] prefix the input with \"Ctrl+\". For a key combination with [Alt] prefix the input with \"Alt+\".\n" +
          "Each combination of \"Ctrl+\" and \"Alt+\" is possible.\n" +
          "An input is a printable character in lowercase or uppercase or one of the following special keys (note that not all keyboards support all special keys):\n" +
          Joiner.on(",")
              .join(Iterables
                  .transform(Iterables
                          .filter(Arrays.asList(KeyType.values()),
                              keyType -> keyType != KeyType.Character && keyType != KeyType.Unknown && keyType != KeyType.CursorLocation && keyType != KeyType.EOF),
                      keyType -> keyType.name())) + "," + KeyStrokes.SPACE + "\n" +
          "";
      inputMappings.store(writer, comments);
    } catch (final Exception exception) {
      throw new RuntimeException("Unable to write input mapping file.", exception);
    }
  }

}
