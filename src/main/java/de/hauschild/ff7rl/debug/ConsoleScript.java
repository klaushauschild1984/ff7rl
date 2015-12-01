/* 
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.debug;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import groovy.lang.Script;

/**
 * @author Klaus Hauschild
 */
public class ConsoleScript extends Script {

    public String describe(final Object object) {
        final StringBuilder builder = new StringBuilder();
        builder.append(object.getClass().getName() + ":");
        for (final Field field : ConsoleScriptHelper.getFields(object)) {
            builder.append("\n  ");
            builder.append(field.getName());
            builder.append(": ");
            builder.append(field.getType().getSimpleName());
        }
        for (final Method method : ConsoleScriptHelper.getMethods(object)) {
            builder.append("\n  ");
            builder.append(method.getName());
            builder.append("(");
            builder.append(Joiner.on(", ").join(
                    Iterables.transform(Arrays.asList(method.getParameterTypes()), Class::getSimpleName)));
            builder.append("): ");
            builder.append(method.getReturnType().getSimpleName());
        }
        return builder.toString();
    }

    public void exit() {
        System.exit(666);
    }

    @Override
    public Object run() {
        return null;
    }

}
