/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.input;

import org.testng.annotations.Test;

import java.io.File;

/**
 * @author Klaus Hauschild
 */
public class InputMappingTest {

  @Test
  public void writeInputFileTest() {
    final File inputMappingFile = new File("input.mapping");
    if (inputMappingFile.exists()) {
      inputMappingFile.delete();
    }
    new InputMapping();
  }

}