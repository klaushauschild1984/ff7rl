/* 
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.debug;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import de.hauschild.ff7rl.state.State;
import groovy.lang.Binding;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Klaus Hauschild
 */
public class Console extends JFrame {

    private static final Logger LOGGER       = LoggerFactory.getLogger(Console.class);

    private static boolean      ENABLED      = false;
    private static boolean      OPENED       = false;
    private static Binding      CONSOLE_BINDING;

    private final JTextArea     output       = new JTextArea();
    private final JTextField    input        = new JTextField();
    private final InputHistory  inputHistory = new InputHistory();

    private Console() {
        super("Debug Console");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setUndecorated(true);
        add(input, BorderLayout.SOUTH);
        add(new JScrollPane(output), BorderLayout.CENTER);
        setSize(800, 600);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width - getWidth(), screenSize.height - getHeight() - 40);
        DefaultCaret caret = (DefaultCaret) output.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        output.setEditable(false);
        output.setFocusable(false);
        output.setText("use 'describe(...)' to find out more\n\r\n\r");
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(final WindowEvent event) {
                input.requestFocus();
            }

        });
        final Font monospacedFont = new Font("monospaced", Font.PLAIN, 16);
        input.setFont(monospacedFont);
        output.setFont(monospacedFont);
        input.addKeyListener(new ConsoleKeyAdapter());
        input.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
    }

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

    private class ConsoleKeyAdapter extends KeyAdapter {

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
                    evaluateInput();
                    break;
                case KeyEvent.VK_TAB:
                    autcompleteInput();
                    break;
            }
        }

        private void evaluateInput() {
            final String inputText = input.getText();
            input.setText("");
            // store in history
            inputHistory.store(inputText);
            // evaluate
            Object result = evaluateInput(inputText);
            // print result
            output.append(String.format("> %s%n%s%n%n", inputText, MoreObjects.firstNonNull(result, "(void)")));
        }

        private Object evaluateInput(final String expression) {
            try {
                return new GroovyShell(CONSOLE_BINDING, ConsoleScriptHelper.getCompilerConfiguration()).evaluate(expression);
            } catch (final GroovyRuntimeException exception) {
                LOGGER.error(String.format("Error while evaluating [%s]", expression), exception);
                return exception.getMessage();
            }
        }

        private void autcompleteInput() {
            String inputText = input.getText();
            String expressionContext;
            String expression;
            if (!inputText.contains(".")) {
                expressionContext = "this";
                expression = inputText;
            } else {
                int lastIndexOfDot = inputText.lastIndexOf(".");
                expressionContext = inputText.substring(0, lastIndexOfDot).trim();
                expression = inputText.substring(lastIndexOfDot + 1).trim();
            }
            LOGGER.trace("expressionContext: {}", expressionContext);
            final List<String> suggestions = Lists.newArrayList();
            suggestions.addAll(CONSOLE_BINDING.getVariables().keySet());
            Object contextObject = evaluateInput(expressionContext);
            if (!(contextObject instanceof Void)) {
                suggestions.addAll(ConsoleScriptHelper.getFields(contextObject).stream().map(Field::getName)
                        .collect(Collectors.toList()));
                suggestions.addAll(ConsoleScriptHelper
                        .getMethods(contextObject)
                        .stream()
                        .map(method -> method.getName()
                                + "("
                                + Joiner.on(", ").join(
                                        Arrays.asList(method.getParameterTypes()).stream().map(parameterType -> "_")
                                                .collect(Collectors.toList())) + ")").collect(Collectors.toList()));
            }
            final List<String> filteredSuggestions = suggestions.stream().sorted(String.CASE_INSENSITIVE_ORDER)
                    .filter(suggestion -> suggestion.startsWith(expression)).collect(Collectors.toList());
            LOGGER.trace("suggestions: {}", filteredSuggestions);
            if (filteredSuggestions.isEmpty()) {
                return;
            }
            JPopupMenu popup = new JPopupMenu();
            filteredSuggestions.forEach(suggestion -> {
                JMenuItem menuItem = new JMenuItem(new AbstractAction(suggestion) {

                    @Override
                    public void actionPerformed(ActionEvent event) {
                        input.setText(input.getText() + event.getActionCommand().substring(expression.length()));
                    }

                });
                popup.add(menuItem);
            });
            try {
                Rectangle caretPosition = input.modelToView(input.getCaretPosition());
                popup.show(input, caretPosition.x, caretPosition.y);
            } catch (BadLocationException exception) {
                LOGGER.error(exception.getMessage());
            }
        }

    }

}
