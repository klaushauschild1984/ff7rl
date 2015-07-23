/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.assets;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Klaus Hauschild
 */
public interface InputStreamProvider {

  InputStream openInputStream();

  void close() throws IOException;

}
