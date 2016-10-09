/* 
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl;

import de.hauschild.ff7rl.assets.images.Images;
import de.hauschild.ff7rl.assets.images.ScreenImage;

/**
 * @author Klaus Hauschild
 */
public enum Actor {

    CLOUD("map/cloud"),

    TIFA(null),

    BARRET(null),

    AERIS(null),

    RED_XIII(null),

    CAIT_SITH(null),

    CID(null),

    YUFIE(null),

    VINCENT(null),

    SEPHIROTH(null),

    ;

    private String imageName;

    Actor(final String imageName) {
        this.imageName = imageName;
    }

    public ScreenImage getImage() {
        return Images.getScreenImage(imageName);
    }

}
