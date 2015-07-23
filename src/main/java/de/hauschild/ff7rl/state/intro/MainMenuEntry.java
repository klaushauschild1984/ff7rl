/* Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2014
 */
package de.hauschild.ff7rl.state.intro;

/**
 * @author Klaus Hauschild
 */
enum MainMenuEntry {

  NEW_GAME("New Game"),

  CONTINUE("Continue"),;

  private final String label;

  MainMenuEntry(final String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return label;
  }

}
