/* Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2014
 */
package de.hauschild.ff7rl.assets;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Klaus Hauschild
 */
public class Resources {

  private static final Logger LOGGER = LoggerFactory.getLogger(Resources.class);

  private static final Map<String, InputStreamProvider> RESOURCES = Maps.newHashMap();

  static {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      for (final InputStreamProvider resource : RESOURCES.values()) {
        LOGGER.debug("Closing resource [{}].", resource);
        try {
          resource.close();
        } catch (final Exception exception) {
          LOGGER.error(String.format("Unable to close resource [%s].", resource), exception);
        }
      }
    }, Resources.class.getSimpleName()));
  }

  public static InputStreamProvider getInputStream(final String assetPath, final String resourceName) {
    final StringBuilder resourcePathBuilder = new StringBuilder();
    if (assetPath != null) {
      resourcePathBuilder.append(assetPath);
      resourcePathBuilder.append("/");
    }
    resourcePathBuilder.append(resourceName);
    final String resourcePath = resourcePathBuilder.toString();
    LOGGER.debug("Requesting resource [{}]", resourcePath);
    InputStreamProvider inputStreamProvider = RESOURCES.get(resourcePath);
    if (inputStreamProvider == null) {
      inputStreamProvider = loadInputStream(resourcePath);
    }
    return inputStreamProvider;
  }

  private static InputStreamProvider loadInputStream(final String resourcePath) {
    LOGGER.debug("Loading resource [{}]", resourcePath);
    final InputStreamProvider inputStreamProvider = new CollectiveInputStreamProvider(resourcePath);
    RESOURCES.put(resourcePath, inputStreamProvider);
    return inputStreamProvider;
  }

}
