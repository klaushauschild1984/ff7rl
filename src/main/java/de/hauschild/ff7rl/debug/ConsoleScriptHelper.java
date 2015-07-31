/* 
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.debug;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @author Klaus Hauschild
 */
public class ConsoleScriptHelper {

  private static final Set<String> IGNORE_BY_CLASSNAME = ImmutableSet.<String>builder()
      .add(Object.class.getName())
      .add(Script.class.getName())
      .add(GroovyObjectSupport.class.getName())
      .add("Script1")
      .build();

  private ConsoleScriptHelper() {
  }

  public static CompilerConfiguration getCompilerConfiguration() {
    final CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
    compilerConfiguration.setScriptBaseClass(ConsoleScript.class.getName());
    return compilerConfiguration;
  }

  public static List<Field> getFields(final Object object) {
    final List<Field> fields = Lists.newArrayList();
    // TODO optimize field access: ReflectionUtils.doWithFields
    for (final Field field : object.getClass().getDeclaredFields()) {
      if (IGNORE_BY_CLASSNAME.contains(field.getDeclaringClass().getName())) {
        continue;
      }
      fields.add(field);
    }
    return fields;
  }

  public static List<Method> getMethods(final Object object) {
    final List<Method> methods = Lists.newArrayList();
    // TODO optimize method access: ReflectionUtils.doWithMethods
    for (final Method method : object.getClass().getMethods()) {
      if (IGNORE_BY_CLASSNAME.contains(method.getDeclaringClass().getName())) {
        continue;
      }
      methods.add(method);
    }
    return methods;
  }

}
