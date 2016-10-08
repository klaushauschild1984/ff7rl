/* 
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.debug;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.reflections.ReflectionUtils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import groovy.lang.GroovyObject;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.Script;

/**
 * @author Klaus Hauschild
 */
enum ConsoleScriptHelper {

    ;

    private static final Set<String> IGNORE_BY_CLASSNAME = ImmutableSet.<String>builder() //
            .add(Object.class.getName()) //
            .add(Script.class.getName()) //
            .add(GroovyObject.class.getName()) //
            .add(GroovyObjectSupport.class.getName()) //
            .add("Script1") //
            .build();

    public static CompilerConfiguration getCompilerConfiguration() {
        final CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setScriptBaseClass(ConsoleScript.class.getName());
        return compilerConfiguration;
    }

    public static List<Field> getFields(final Object object) {
        List<Field> fields = ReflectionUtils.getAllFields(object.getClass()).stream()
                .filter(field -> !IGNORE_BY_CLASSNAME.contains(field.getDeclaringClass().getName())
                        && !field.getName().equals("this$0"))
                .sorted((field1, field2) -> field1.getName().compareTo(field2.getName())).collect(Collectors.toList());
        Set<String> uniqueNames = Sets.newHashSet();
        return fields.stream().filter(field -> {
            if (uniqueNames.contains(field.getName())) {
                return false;
            }
            uniqueNames.add(field.getName());
            return true;
        }).collect(Collectors.toList());
    }

    public static List<Method> getMethods(final Object object) {
        List<Method> methods = ReflectionUtils.getAllMethods(object.getClass()).stream()
                .filter(method -> !IGNORE_BY_CLASSNAME.contains(method.getDeclaringClass().getName()))
                .sorted((method1, method2) -> method1.getName().compareTo(method2.getName())).collect(Collectors.toList());
        Set<String> uniqueNames = Sets.newHashSet();
        // TODO handle overloaded methods with same name correct (now they will filtered)
        return methods.stream().filter(method -> {
            if (uniqueNames.contains(method.getName())) {
                return false;
            }
            uniqueNames.add(method.getName());
            return true;
        }).filter(method -> !method.getDeclaringClass().equals(ConsoleScript.class) || !method.getName().equals("run"))
                .collect(Collectors.toList());
    }

}
