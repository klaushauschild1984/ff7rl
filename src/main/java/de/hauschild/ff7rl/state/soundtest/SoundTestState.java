/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.state.soundtest;

import com.google.common.collect.Lists;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Klaus Hauschild
 */
public class SoundTestState {

    private static final Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(
                                                         ClasspathHelper.forClassLoader()).setScanners(new ResourcesScanner()));

    private static List<String> getAllSounds() {
        final List<String> sounds = Lists.newArrayList(reflections.getResources(Pattern.compile(".*\\.mp3")));
        Collections.sort(sounds);
        return sounds;
    }

    public static void main(final String[] args) {
        final List<String> sounds = getAllSounds();
        System.out.println(sounds.size());
    }

}
