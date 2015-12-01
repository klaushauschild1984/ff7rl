/* 
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.debug;

import com.google.common.collect.Lists;

/**
 * @author Klaus Hauschild
 */
class InputHistory {

    private final java.util.List<String> inputHistory        = Lists.newArrayList();
    private int                          inputHistoryPointer = 0;

    public void store(final String input) {
        inputHistory.add(input);
        inputHistoryPointer = inputHistory.size() - 1;
    }

    public String previous() {
        final String previous = get();
        inputHistoryPointer--;
        if (inputHistoryPointer == -1) {
            inputHistoryPointer = 0;
        }
        return previous;
    }

    public String next() {
        final String next = get();
        inputHistoryPointer++;
        if (inputHistoryPointer == inputHistory.size()) {
            inputHistoryPointer = inputHistory.size() - 1;
        }
        return next;
    }

    private String get() {
        return inputHistory.get(inputHistoryPointer);
    }

}
