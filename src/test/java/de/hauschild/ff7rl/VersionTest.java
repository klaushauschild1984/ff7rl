package de.hauschild.ff7rl;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Klaus Hauschild
 */
public class VersionTest {

  @Test
  public void versionTest() {
    final String version = Version.get();
    assertTrue(version.contains("-SNAPSHOT"));
  }

}