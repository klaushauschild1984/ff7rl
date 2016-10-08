/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

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
        InputStreamProvider inputStreamProvider = Resources.getInputStream(null, "something that isn't there.txt");
        try {
            inputStreamProvider.openInputStream();
            fail("Exception was expected");
        } catch (final Exception exception) {
            // exception was expected
        }
    }

    @Test
    public void loadResourceTest() {
        Resources.getInputStream(null, "version.properties").openInputStream();
        Resources.getInputStream("assets/sounds", "1-01 Prelude.mp3").openInputStream();
    }

}
