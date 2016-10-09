/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.context;

import com.googlecode.lanterna.TextColor.RGB;

import de.hauschild.ff7rl.state.StateType;

/**
 * @author Klaus Hauschild
 */
public enum KernelContext {

    ;

    private static final String COLORS     = "ff7rl.context.general.colors";
    private static final String LAST_STATE = "ff7rl.context.general.last-state";
    private static final String REGION     = "ff7rl.context.general.region";
    private static final String STEPS      = "ff7rl.context.general.steps";
    private static final String GIL        = "ff7rl.context.general.gil";

    public static RGB getColor(final Context context) {
        final String colorsString = context.get(COLORS);
        final String[] split = colorsString.split(",");
        return new RGB(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    public static void setColors(final Context context, final RGB color) {
        context.set(COLORS, String.format("%s,%s,%s", color.getRed(), color.getGreen(), color.getBlue()));
    }

    public static void setLastState(final Context context, final StateType type) {
        context.set(LAST_STATE, type);
    }

    public static StateType getLastState(final Context context) {
        return context.get(LAST_STATE);
    }

    public static String getRegion(final Context context) {
        return (String) context.get(KernelContext.REGION);
    }

    public static void setRegion(final Context context, final String region) {
        context.set(KernelContext.REGION, region);
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
