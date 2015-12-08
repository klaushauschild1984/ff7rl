/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.rooms;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
        return (T) Proxy.newProxyInstance(PROXY_TYPE.getClassLoader(), new Class[] { PROXY_TYPE }, (proxy, method, args) -> {
            try {
                Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
                return targetMethod.invoke(target, args);
            } catch (final Exception exception) {
                LOGGER.warn("Method/property [{}] not defined [{}] room script.", method.getName(), roomName);
                return null;
            }
        });
    }

}
