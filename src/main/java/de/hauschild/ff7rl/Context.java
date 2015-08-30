/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * @author Klaus Hauschild
 */
public class Context {

  private static final Gson GSON = new GsonBuilder().create();

  private long stepCounter;
  private long gil;
  private List<Actor> currentParty = Lists.newArrayList();

  public static Context createStartContext() {
    return new Context();
  }

  public static Context fromJson(final String json) {
    return GSON.fromJson(json, Context.class);
  }

  public static String toJson(final Context context) {
    return GSON.toJson(context);
  }

  private Context() {
    currentParty.add(Actor.CLOUD);
    currentParty.add(Actor.BARRET);
  }

  public long getGil() {
    return gil;
  }

  public void updateGil(final long increment) {
    if (gil + increment < 0) {
      throw new IllegalArgumentException("Not enough gil.");
    }
    gil += increment;
  }

  public long getStepCounter() {
    return stepCounter;
  }

  public void incrementStepCounter() {
    stepCounter++;
  }

}
