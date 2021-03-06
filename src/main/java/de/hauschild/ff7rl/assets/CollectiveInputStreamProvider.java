/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * @author Klaus Hauschild
 */
public class CollectiveInputStreamProvider implements InputStreamProvider {

    private final String           resourcePath;
    private final Set<InputStream> inputStreams = Sets.newHashSet();

    public CollectiveInputStreamProvider(final String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public InputStream openInputStream() {
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new RuntimeException(String.format("Unable to open resource [%s].", this));
        }
        inputStreams.add(inputStream);
        return inputStream;
    }

    @Override
    public void close() throws IOException {
        for (final InputStream inputStream : inputStreams) {
            inputStream.close();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(resourcePath);
        if (!inputStreams.isEmpty()) {
            builder.append(String.format(" (%s)", inputStreams.size()));
        }
        return builder.toString();
    }
}
