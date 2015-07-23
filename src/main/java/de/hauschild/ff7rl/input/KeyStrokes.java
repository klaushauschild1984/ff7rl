/* Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2014
 */
package de.hauschild.ff7rl.input;

import com.google.common.base.Splitter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

/**
 * @author Klaus Hauschild
 */
class KeyStrokes {

  public static final String SPACE = "Space";
  private static final char SPACE_CHARACTER = ' ';

  private static final String SEPARATOR = "+";
  private static final String CTRL = "Ctrl";
  private static final String ALT = "Alt";

  public static KeyStroke fromString(final String keyStrokeAsString) {
    KeyType keyType = null;
    Character character = null;
    boolean ctrlDown = false;
    boolean altDown = false;
    for (final String keyStrokePart : Splitter.on(SEPARATOR).split(keyStrokeAsString)) {
      if (keyStrokePart.equals(CTRL)) {
        ctrlDown = true;
        continue;
      }
      if (keyStrokePart.equals(ALT)) {
        altDown = true;
        continue;
      }
      if (keyStrokePart.equals(SPACE)) {
        character = SPACE_CHARACTER;
        continue;
      }
      try {
        keyType = KeyType.valueOf(keyStrokePart);
      } catch (final IllegalArgumentException exception) {
        character = keyStrokePart.charAt(0);
      }
    }
    if (keyType != null && character == null) {
      return new KeyStroke(keyType, ctrlDown, altDown);
    } else if (keyType == null && character != null) {
      return new KeyStroke(character, ctrlDown, altDown);
    }
    throw new IllegalArgumentException(String.format("Unable to recognize key stroke: %s", keyStrokeAsString));
  }

  public static String toString(final KeyStroke keyStroke) {
    final StringBuilder builder = new StringBuilder();
    if (keyStroke.isCtrlDown()) {
      builder.append(CTRL).append(SEPARATOR);
    }
    if (keyStroke.isAltDown()) {
      builder.append(ALT).append(SEPARATOR);
    }
    final KeyType keyType = keyStroke.getKeyType();
    if (keyType != null && keyType != KeyType.Character) {
      builder.append(keyType);
    }
    final Character character = keyStroke.getCharacter();
    if (character != null && keyType == KeyType.Character && character != SPACE_CHARACTER) {
      builder.append(character);
    }
    if (character != null && character == SPACE_CHARACTER) {
      builder.append(SPACE);
    }
    return builder.toString();
  }

}
