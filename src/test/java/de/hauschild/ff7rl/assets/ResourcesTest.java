package de.hauschild.ff7rl.assets;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * @author Klaus Hauschild
 */
public class ResourcesTest {

  @Test
  public void requestSameResourceMoreThanOneTimeTest() {
    final InputStreamProvider providerA = Resources.getInputStream(null, "version.properties");
    final InputStreamProvider providerB = Resources.getInputStream(null, "version.properties");
    assertTrue(providerA == providerB);
  }

  @Test
  public void errorHandlingTest() {
    try {
      Resources.getInputStream(null, "something that isn't there.txt").openInputStream();
      fail("Exception was expected");
    } catch (final Exception exception) {
      // exception was expected
    }
  }

  @Test
  public void loadResourceTest() {
    Resources.getInputStream(null, "version.properties").openInputStream();
    Resources.getInputStream("assets/sounds", "Prelude.mp3").openInputStream();
  }

}