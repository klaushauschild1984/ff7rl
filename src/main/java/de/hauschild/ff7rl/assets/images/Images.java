/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.images;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import de.hauschild.ff7rl.assets.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author Klaus Hauschild
 */
public enum Images {

    ;

    private static final Logger                            LOGGER = LoggerFactory.getLogger(Images.class);

    private static final LoadingCache<String, ScreenImage> IMAGES = CacheBuilder.newBuilder().build(new ImagesLoader());

    public static ScreenImage getImage(final String imageName) {
        LOGGER.debug("Requesting image [{}]", imageName);
        try {
            return IMAGES.get(imageName);
        } catch (ExecutionException exception) {
            throw new RuntimeException(String.format("Unable to load image [%s].", imageName), exception);
        }
    }

    private static final class ImagesLoader extends CacheLoader<String, ScreenImage> {

        private static final String IMAGES_ASSETS__PATH = "assets/images";

        @Override
        public ScreenImage load(@ParametersAreNonnullByDefault
        final String imageName) throws Exception {
            LOGGER.debug("Load image [{}]", imageName);
            final Color[][] background = getBackground(imageName);
            final Color[][] foreground = getForeground(imageName);
            final List<String> text = getText(imageName);
            return new ScreenImage(background, foreground, text);
        }

        private List<String> getText(final String imageName) {
            try (InputStream inputStream = Resources.getInputStream(IMAGES_ASSETS__PATH, String.format("%s-fg.txt", imageName))
                    .openInputStream()) {
                return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(
                        Collectors.toList());
            } catch (IOException exception) {
                throw new RuntimeException(String.format("Unable to load image [%s].", imageName), exception);
            }
        }

        private Color[][] getBackground(final String imageName) {
            return getColors(imageName, "%s-bg.png");
        }

        private Color[][] getForeground(final String imageName) {
            return getColors(imageName, "%s-fg.png");
        }

        private Color[][] getColors(final String imageName, final String nameTemplate) {
            final String colorsImageName = String.format(nameTemplate, imageName);
            BufferedImage colorsImage = loadImage(colorsImageName);
            return getColors(colorsImage);
        }

        private BufferedImage loadImage(final String imageName) {
            try {
                return ImageIO.read(Resources.getInputStream(IMAGES_ASSETS__PATH, imageName).openInputStream());
            } catch (IOException exception) {
                throw new RuntimeException(String.format("Unable to load image [%s].", imageName), exception);
            }
        }

        private Color[][] getColors(final BufferedImage image) {
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            final Color[][] colors = new Color[width][height];
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    colors[i][j] = new Color(image.getRGB(i, j), true);
                }
            }
            return colors;
        }

    }

}
