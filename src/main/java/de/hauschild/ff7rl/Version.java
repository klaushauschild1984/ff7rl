/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl;

import de.hauschild.ff7rl.assets.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Klaus Hauschild
 */
public class Version {

  private static final Logger LOGGER = LoggerFactory.getLogger(Version.class);

  public static String get() {
    try {
      final Properties versionProperties = new Properties();
      final InputStream versionInputStream = Resources.getInputStream(null, "version.properties").openInputStream();
      versionProperties.load(versionInputStream);
      return versionProperties.getProperty("version");
    } catch (final Exception exception) {
      LOGGER.error("Unable to determine version.", exception);
      return "<unknown>";
    }
  }

}
