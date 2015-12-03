/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.sounds;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.hauschild.ff7rl.assets.Resources;

/**
 * @author Klaus Hauschild
 */
public enum Sounds {

    ;

    public static final String              SOUND_ASSETS__PATH = "assets/sounds";

    private static final Logger             LOGGER             = LoggerFactory.getLogger(Sounds.class);

    private static final Map<String, Sound> SOUNDS             = Maps.newHashMap();
    private static final Reflections        REFLECTIONS        = new Reflections(new ConfigurationBuilder().setUrls(
                                                                       ClasspathHelper.forClassLoader()).setScanners(
                                                                       new ResourcesScanner()));
    private static boolean                  MUTE               = false;

    public static List<String> getAllSounds() {
        final List<String> sounds = Lists.newArrayList(REFLECTIONS.getResources(Pattern.compile(".*\\.mp3")));
        Collections.sort(sounds);
        return sounds.stream().map(sound -> sound.replace(Sounds.SOUND_ASSETS__PATH + "/", "")).collect(Collectors.toList());
    }

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
            final InputStream inputStream = Resources.getInputStream(SOUND_ASSETS__PATH, soundName).openInputStream();
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
