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