/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.images;

import org.testng.annotations.Test;

/**
 * @author Klaus Hauschild
 */
public class ScreenImageTest {

    @Test
    public void loadImageTest() {
        Images.getScreenImage("map/cloud");
    }

}
