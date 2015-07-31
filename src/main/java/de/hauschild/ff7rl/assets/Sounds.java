/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.assets;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Map;

/**
 * @author Klaus Hauschild
 */
public class Sounds {

  private static final Logger LOGGER = LoggerFactory.getLogger(Sounds.class);

  private static final Map<String, Sound> SOUNDS = Maps.newHashMap();
  private static boolean MUTE = false;

  private Sounds() {
  }

  public static Sound getSound(final String soundName) {
    LOGGER.debug("Requesting sound [{}]", soundName);
    Sound sound = SOUNDS.get(soundName);
    if (sound == null) {
      sound = loadSound(soundName);
    }
    return sound;
  }

  private static Sound loadSound(final String soundName) {
    if (MUTE) {
      return new MutedSound();
    }
    LOGGER.debug("Load sound [{}]", soundName);
    try {
      final InputStream inputStream = Resources.getInputStream("assets/sounds", soundName).openInputStream();
      final Sound sound = new JavaXSoundSampledSound(inputStream);
      SOUNDS.put(soundName, sound);
      return sound;
    } catch (final Exception exception) {
      throw new RuntimeException(String.format("Unable to load sound [%s].", soundName), exception);
    }
  }

  public static void mute() {
    MUTE = true;
    LOGGER.info("Game is muted.");
  }

}
