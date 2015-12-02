/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.input;

import java.io.File;

import org.testng.annotations.Test;

/**
 * @author Klaus Hauschild
 */
public class InputMappingTest {

    @Test
    public void writeInputMappingFileTest() {
        deleteInputMappingFile();
        new InputMapping();
    }

    @Test
    public void readInputMappingFileTest() {
        deleteInputMappingFile();
        new InputMapping();
        new InputMapping();
    }

    private void deleteInputMappingFile() {
        final File inputMappingFile = new File("input.mapping");
        if (inputMappingFile.exists()) {
            if (!inputMappingFile.delete()) {
                throw new RuntimeException(String.format("Unable to delete [%s].", inputMappingFile));
            }
        }
    }

}
