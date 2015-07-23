package de.hauschild.ff7rl.ui;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Klaus Hauschild
 */
public class ScreenMenuTest {

  enum Menu {
    A, B, C
  }

  @Test
  public void cursorPositionTest() {
    final ScreenMenu<Menu> screenMenu = new ScreenMenu<>(Menu.values(), 0, 0);
    assertTrue(screenMenu.select() == Menu.A);
    screenMenu.next();
    assertTrue(screenMenu.select() == Menu.B);
    screenMenu.next();
    assertTrue(screenMenu.select() == Menu.C);
    screenMenu.next();
    assertTrue(screenMenu.select() == Menu.A);
    screenMenu.previous();
    assertTrue(screenMenu.select() == Menu.C);
    screenMenu.previous();
    assertTrue(screenMenu.select() == Menu.B);
    screenMenu.previous();
    assertTrue(screenMenu.select() == Menu.A);
  }

}