/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.input;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

/**
 * @author Klaus Hauschild
 */
public class KeyStrokesTest {

    @Test(dataProvider = "keyStrokesTestDataProvider")
    public void fromStringTest(final KeyStroke expectedKeyStroke, final String string) {
        assertEquals(KeyStrokes.fromString(string), expectedKeyStroke);
    }

    @Test(dataProvider = "keyStrokesTestDataProvider")
    public void toStringTest(final KeyStroke keyStroke, final String expectedString) {
        assertEquals(KeyStrokes.toString(keyStroke), expectedString);
    }

    @DataProvider
    public Object[][] keyStrokesTestDataProvider() {
        return new Object[][] { { new KeyStroke(KeyType.Enter), "Enter", },
                { new KeyStroke(KeyType.ArrowDown, true, false), "Ctrl+ArrowDown", },
                { new KeyStroke(KeyType.Escape, false, true), "Alt+Escape", },
                { new KeyStroke(' ', true, true), "Ctrl+Alt+Space", }, { new KeyStroke('w', false, false), "w", },
                { new KeyStroke('A', true, false), "Ctrl+A", }, { new KeyStroke('s', false, true), "Alt+s", },
                { new KeyStroke('D', true, true), "Ctrl+Alt+D", }, };
    }

}
