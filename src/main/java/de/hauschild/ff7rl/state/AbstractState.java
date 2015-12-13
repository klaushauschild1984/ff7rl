/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hauschild.ff7rl.context.Context;

/**
 * @author Klaus Hauschild
 */
public abstract class AbstractState implements State {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractState.class);

    private final StateType     type;
    private final Context       context;

    public AbstractState(final StateType type, final Context context) {
        this.type = type;
        this.context = context;
    }

    @Override
    public StateType getType() {
        return type;
    }

    @Override
    public void enter() {
        LOGGER.debug("Enter [{}] state.", this);
    }

    @Override
    public void leave() {
        LOGGER.debug("Leave [{}] state.", this);
    }

    @Override
    public String toString() {
        return type.toString();
    }

    protected Context getContext() {
        return context;
    }

}
