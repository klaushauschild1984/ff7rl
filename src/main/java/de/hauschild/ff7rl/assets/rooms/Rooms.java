/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.rooms;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import de.hauschild.ff7rl.assets.Resources;
import de.hauschild.ff7rl.assets.images.Image;
import de.hauschild.ff7rl.assets.images.Images;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;

/**
 * @author Klaus Hauschild
 */
public enum Rooms {

    ;

    private static final Logger                     LOGGER        = LoggerFactory.getLogger(Rooms.class);

    private static final GroovyClassLoader          SCRIPT_LOADER = new GroovyClassLoader();

    private static final LoadingCache<String, Room> ROOMS         = CacheBuilder.newBuilder().build(new RoomsLoader());

    public static Room getRoom(final String roomName) {
        LOGGER.debug("Requesting room [{}]", roomName);
        try {
            return ROOMS.get(roomName);
        } catch (ExecutionException exception) {
            throw new RuntimeException(String.format("Unable to load room [%s].", roomName), exception);
        }
    }

    private static final class RoomsLoader extends CacheLoader<String, Room> {

        private static final String ROOMS_ASSETS__PATH = "assets/rooms";

        @Override
        public Room load(final String roomName) throws Exception {
            LOGGER.debug("Load room [{}]", roomName);
            final Image roomImage = Images.getImage(ROOMS_ASSETS__PATH, roomName);
            final boolean[][] walls = getWalls(roomName);
            RoomScript roomScript = getScript(roomName);
            return new Room(roomImage, walls, roomScript);
        }

        private boolean[][] getWalls(final String roomName) {
            final BufferedImage wallsImage = Images.loadImage(ROOMS_ASSETS__PATH, String.format("%s/walls.png", roomName));
            final Color[][] colors = Images.getColors(wallsImage);
            int width = colors.length;
            int height = colors[0].length;
            final boolean[][] walls = new boolean[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = new Color(wallsImage.getRGB(x, y), true);
                    walls[x][y] = color.getAlpha() == 255;
                }
            }
            return walls;
        }

        private RoomScript getScript(final String roomName) {
            final String codeBase = String.format("%s.script.groovy", roomName);
            final String scriptName = String.format("%s/script.groovy", roomName);
            try (final InputStreamReader scriptReader = new InputStreamReader(
                    Resources.getInputStream(ROOMS_ASSETS__PATH, scriptName).openInputStream())) {
                final GroovyCodeSource groovyCodeSource = new GroovyCodeSource(scriptReader, codeBase, codeBase);
                final Class scriptClass = SCRIPT_LOADER.parseClass(groovyCodeSource);
                final Object roomScript = scriptClass.newInstance();
                return RoomScriptProxy.build(roomScript, roomName);
            } catch (final Exception exception) {
                throw new RuntimeException(String.format("Unable to load room's [%s] script.", roomName), exception);
            }
        }

    }

}
