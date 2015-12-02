/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

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
