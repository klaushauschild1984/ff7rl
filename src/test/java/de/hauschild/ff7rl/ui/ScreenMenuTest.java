/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.ui;

import com.google.common.collect.Lists;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author Klaus Hauschild
 */
public class ScreenMenuTest {

  @Test
  public void cursorPositionTest() {
    final List<ScreenMenu.Entry> entries = Lists.newArrayList(new ScreenMenu.Entry("A"), new ScreenMenu.Entry("B"), new ScreenMenu.Entry("C"));
    final ScreenMenu<?> screenMenu = new ScreenMenu<>(entries, 0, 0);
    assertEquals(screenMenu.select().getLabel(), "A");
    screenMenu.next();
    assertEquals(screenMenu.select().getLabel(), "B");
    screenMenu.next();
    assertEquals(screenMenu.select().getLabel(), "C");
    screenMenu.next();
    assertEquals(screenMenu.select().getLabel(), "A");
    screenMenu.previous();
    assertEquals(screenMenu.select().getLabel(), "C");
    screenMenu.previous();
    assertEquals(screenMenu.select().getLabel(), "B");
    screenMenu.previous();
    assertEquals(screenMenu.select().getLabel(), "A");
  }

}