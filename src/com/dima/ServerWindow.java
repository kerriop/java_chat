package com.dima;

import javax.swing.*;
import java.awt.event.*;

/**
 * Решено не использовать GUI на сервере (кроме стартового)
 * @see ClientWindow
 * @see MainWindow
 */
@Deprecated
public class ServerWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea списокПользователейTextArea;
    private JTextField когоВыгнатьTextField;

    public ServerWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }
    
}
