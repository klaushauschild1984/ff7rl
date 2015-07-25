/* 
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail,com>, 2015
 */
package de.hauschild.ff7rl.debug;

import com.google.common.base.MoreObjects;
import de.hauschild.ff7rl.state.State;
import groovy.lang.Binding;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Klaus Hauschild
 */
public class Console extends JFrame {

  public static void main(final String[] args) {
    SwingUtilities.invokeLater(() -> new Console().setVisible(true));
  }

  private static boolean ENABLED = false;
  private static boolean OPENED = false;
  private static Binding CONSOLE_BINDING;

  public static void enableDebugConsole() {
    ENABLED = true;
  }

  public static void openDebugConsole(final State state) {
    if (!ENABLED || OPENED) {
      return;
    }
    new Console().setVisible(true);
    OPENED = true;
    rebind(state);
  }

  public static void rebind(final State state) {
    if (!ENABLED || !OPENED) {
      return;
    }
    CONSOLE_BINDING = new Binding();
    CONSOLE_BINDING.setVariable("state", state);
  }

  private final JTextArea output = new JTextArea();
  private final JTextField input = new JTextField();

  private final InputHistory inputHistory = new InputHistory();

  private Console() {
    super("Debug Console");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setUndecorated(true);
    add(input, BorderLayout.SOUTH);
    add(new JScrollPane(output), BorderLayout.CENTER);
    setSize(800, 600);
    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(screenSize.width - getWidth(), screenSize.height - getHeight() - 40);
    output.setEditable(false);
    output.setFocusable(false);
    final Color foreground = new Color(169, 183, 198);
    final Color background = new Color(43, 43, 43);
    input.setForeground(foreground);
    input.setBackground(background);
    input.setCaretColor(new Color(187, 187, 187));
    output.setForeground(foreground);
    output.setBackground(background);
    addWindowListener(new WindowAdapter() {

      @Override
      public void windowOpened(final WindowEvent e) {
        input.requestFocus();
      }

    });
    final Font monospacedFont = new Font("monospaced", Font.PLAIN, 16);
    input.setFont(monospacedFont);
    output.setFont(monospacedFont);
    input.addKeyListener(new KeyAdapter() {

      @Override
      public void keyPressed(final KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_UP:
            input.setText(inputHistory.previous());
            break;
          case KeyEvent.VK_DOWN:
            input.setText(inputHistory.next());
            break;
          case KeyEvent.VK_ENTER:
            final String inputText = input.getText();
            input.setText("");
            // store in history
            inputHistory.store(inputText);
            // evaluate
            Object result;
            try {
              result = new GroovyShell(CONSOLE_BINDING).evaluate(inputText);
            } catch (final GroovyRuntimeException exception) {
              final StringWriter stringWriter = new StringWriter();
              exception.printStackTrace(new PrintWriter(stringWriter));
              result = stringWriter.toString();
            }
            output.append(String.format("> %s%n%s%n%n", inputText, MoreObjects.firstNonNull(result,"(void)")));
            break;
        }
      }
    });
  }

}