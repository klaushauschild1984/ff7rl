/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.context;

import de.hauschild.ff7rl.state.StateType;

/**
 * @author Klaus Hauschild
 */
public enum KernelContext {

    ;

    static final String LAST_STATE = "ff7rl.context.general.last-state";
    static final String REGION     = "ff7rl.context.general.region";
    static final String STEPS      = "ff7rl.context.general.steps";
    static final String GIL        = "ff7rl.context.general.gil";

    public static void setLastState(final Context context, final StateType type) {
        context.set(LAST_STATE, type);
    }

    public static StateType getLastState(final Context context) {
        return context.get(LAST_STATE);
    }

    public static int getGil(final Context context) {
        return context.get(GIL, 0);
    }

    public static int getSteps(final Context context) {
        return context.get(STEPS, 0);
    }

    public static void incrementSteps(final Context context) {
        final int steps = getSteps(context);
        context.set(STEPS, steps + 1);
    }

}
