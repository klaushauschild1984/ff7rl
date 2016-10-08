/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.rooms;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Klaus Hauschild
 */
public enum RoomScriptProxy {

    ;

    private static final Logger   LOGGER     = LoggerFactory.getLogger(RoomScriptProxy.class);

    private static final Class<?> PROXY_TYPE = RoomScript.class;

    public static <T> T build(final Object target, final String roomName) {
        verify(target, roomName);
        return (T) Proxy.newProxyInstance(PROXY_TYPE.getClassLoader(), new Class[] { PROXY_TYPE }, (proxy, method, args) -> {
            try {
                Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
                return targetMethod.invoke(target, args);
            } catch (final Exception exception) {
                if (!(exception instanceof NoSuchMethodException)) {
                    LOGGER.warn(
                            String.format("Error while executing method [%s] not defined [%s] room script.", method, roomName),
                            exception);
                }
                return null;
            }
        });
    }

    private static void verify(final Object target, final String roomName) {
        Arrays.asList(PROXY_TYPE.getDeclaredMethods()).forEach(method -> {
            try {
                target.getClass().getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException exception) {
                LOGGER.warn("Method [{}] not defined in [{}] room script.", method, roomName);
            }
        });
    }

}
