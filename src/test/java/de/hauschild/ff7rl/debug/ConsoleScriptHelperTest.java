/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.debug;

import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author Klaus Hauschild
 */
public class ConsoleScriptHelperTest {

    private class TestClass {

        String string;
        boolean bool;
        int integer;

        String string() {
            return null;
        }

        void nothing() {
        }

    }

    @Test
    public void getFieldsTest() {
        TestClass testClass = new TestClass();
        List<Field> fields = ConsoleScriptHelper.getFields(testClass);
        assertEquals(fields.size(), 3);
        assertEquals(fields.get(0).getName(), "bool");
        assertEquals(fields.get(1).getName(), "integer");
        assertEquals(fields.get(2).getName(), "string");
    }

    @Test
    public void getMethodsTest() {
        TestClass testClass = new TestClass();
        List<Method> methods = ConsoleScriptHelper.getMethods(testClass);
        assertEquals(methods.size(), 2);
        assertEquals(methods.get(0).getName(), "nothing");
        assertEquals(methods.get(1).getName(), "string");
    }

}