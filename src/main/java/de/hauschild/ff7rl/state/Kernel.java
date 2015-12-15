/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.state;

import de.hauschild.ff7rl.context.Context;
import de.hauschild.ff7rl.context.ContextConstants.General;

/**
 * @author Klaus Hauschild
 */
public enum Kernel {

    ;

    public static void setLastState(final Context context, final StateType type) {
        context.set(General.LAST_STATE, type);
    }

    public static StateType getLastState(final Context context) {
        return (StateType) context.get(General.LAST_STATE);
    }

    public static int getGil(final Context context) {
        return (int) context.get(General.GIL, 0);
    }

    public static int getSteps(final Context context) {
        return (int) context.get(General.STEPS, 0);
    }

    public static void incrementSteps(final Context context) {
        final int steps = getSteps(context);
        context.set(General.STEPS, steps + 1);
    }

}
