/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.debug;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * @author Klaus Hauschild
 */
public class InputHistoryTest {

    @Test
    public void inputHistoryTest() {
        final InputHistory inputHistory = new InputHistory();
        inputHistory.store("a");
        inputHistory.store("b");
        inputHistory.store("c");
        assertEquals(inputHistory.previous(), "c");
        assertEquals(inputHistory.previous(), "b");
        assertEquals(inputHistory.previous(), "a");
        assertEquals(inputHistory.previous(), "a");
        assertEquals(inputHistory.next(), "a");
        assertEquals(inputHistory.next(), "b");
        assertEquals(inputHistory.next(), "c");
        assertEquals(inputHistory.next(), "c");
    }

}
