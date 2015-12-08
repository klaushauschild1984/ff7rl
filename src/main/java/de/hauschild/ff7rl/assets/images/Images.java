/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.images;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import de.hauschild.ff7rl.assets.Resources;

/**
 * @author Klaus Hauschild
 */
public enum Images {

    ;

    private static final Logger                                           LOGGER = LoggerFactory.getLogger(Images.class);

    private static final LoadingCache<Entry<String, String>, ScreenImage> IMAGES = CacheBuilder.newBuilder().build(
                                                                                         new ImagesLoader());

    public static ScreenImage getImage(final String imageName) {
        return getImage(null, imageName);
    }

    public static ScreenImage getImage(final String assetRoot, final String imageName) {
        LOGGER.debug("Requesting image [{}]", imageName);
        try {
            return IMAGES.get(new SimpleEntry<>(assetRoot, imageName));
        } catch (ExecutionException exception) {
            throw new RuntimeException(String.format("Unable to load image [%s].", imageName), exception);
        }
    }

    public static BufferedImage loadImage(final String assetRoot, final String imageName) {
        try {
            return ImageIO.read(Resources.getInputStream(assetRoot, imageName).openInputStream());
        } catch (IOException exception) {
            throw new RuntimeException(String.format("Unable to load image [%s].", imageName), exception);
        }
    }

    public static Color[][] getColors(final BufferedImage image) {
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

    private static final class ImagesLoader extends CacheLoader<Entry<String, String>, ScreenImage> {

        private static final String IMAGES_ASSETS__PATH = "assets/images";

        @Override
        public ScreenImage load(final Entry<String, String> assetRootAndImageName) throws Exception {
            final String imageName = assetRootAndImageName.getValue();
            final String assetRoot = MoreObjects.firstNonNull(assetRootAndImageName.getKey(), IMAGES_ASSETS__PATH);
            LOGGER.debug("Load image [{}]", imageName);
            final Color[][] background = getBackground(assetRoot, imageName);
            final Color[][] foreground = getForeground(assetRoot, imageName);
            final List<String> text = getText(assetRoot, imageName);
            return new ScreenImage(background, foreground, text);
        }

        private List<String> getText(final String assetRoot, final String imageName) {
            try (InputStream inputStream = Resources.getInputStream(assetRoot, String.format("%s/foreground.txt", imageName))
                    .openInputStream()) {
                return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(
                        Collectors.toList());
            } catch (IOException exception) {
                throw new RuntimeException(String.format("Unable to load image [%s].", imageName), exception);
            }
        }

        private Color[][] getBackground(final String assetRoot, final String imageName) {
            return getColors(assetRoot, imageName, "%s/background.png");
        }

        private Color[][] getForeground(final String assetRoot, final String imageName) {
            return getColors(assetRoot, imageName, "%s/foreground.png");
        }

        private Color[][] getColors(final String assetRoot, final String imageName, final String nameTemplate) {
            final String colorsImageName = String.format(nameTemplate, imageName);
            BufferedImage colorsImage = loadImage(assetRoot, colorsImageName);
            return Images.getColors(colorsImage);
        }

    }

}
