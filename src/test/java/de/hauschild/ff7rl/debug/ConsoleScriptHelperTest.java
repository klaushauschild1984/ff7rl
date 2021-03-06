/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.debug;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.testng.annotations.Test;

/**
 * @author Klaus Hauschild
 */
public class ConsoleScriptHelperTest {

    @Test
    public void getFieldsTest() {
        final TestClass testClass = new TestClass();
        final List<Field> fields = ConsoleScriptHelper.getFields(testClass);
        assertEquals(fields.size(), 3);
        assertEquals(fields.get(0).getName(), "bool");
        assertEquals(fields.get(1).getName(), "integer");
        assertEquals(fields.get(2).getName(), "string");
    }

    @Test
    public void getMethodsTest() {
        final TestClass testClass = new TestClass();
        final List<Method> methods = ConsoleScriptHelper.getMethods(testClass);
        assertEquals(methods.size(), 2);
        assertEquals(methods.get(0).getName(), "nothing");
        assertEquals(methods.get(1).getName(), "string");
    }

    private class TestClass {

        String  string;
        boolean bool;
        int     integer;

        String string() {
            return null;
        }

        void nothing() {
        }

    }

}
