/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.assets;

import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * @author Klaus Hauschild
 */
public enum Sounds {

    ;

    private static final Logger             LOGGER = LoggerFactory.getLogger(Sounds.class);

    private static final Map<String, Sound> SOUNDS = Maps.newHashMap();
    private static boolean                  MUTE   = false;

    public static Sound getSound(final String soundName) {
        LOGGER.debug("Requesting sound [{}]", soundName);
        Sound sound = SOUNDS.get(soundName);
        if (sound == null) {
            sound = loadSound(soundName);
            SOUNDS.put(soundName, sound);
        }
        if (MUTE) {
            return new MutedSound();
        }
        return sound;
    }

    private static Sound loadSound(final String soundName) {
        LOGGER.debug("Load sound [{}]", soundName);
        try {
            final InputStream inputStream = Resources.getInputStream("assets/sounds", soundName).openInputStream();
            final Sound sound = new JavaXSoundSampledSound(inputStream);
            return sound;
        } catch (final Exception exception) {
            LOGGER.error(String.format("Unable to load sound [%s].", soundName), exception);
            return new MutedSound();
        }
    }

    public static void mute() {
        MUTE = true;
        LOGGER.info("Game is muted.");
    }

}
