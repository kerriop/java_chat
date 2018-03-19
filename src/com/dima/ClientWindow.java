package com.dima;

import com.dima.client.Client;

import javax.swing.*;
import java.awt.event.*;

public class ClientWindow extends JDialog {
    private JPanel contentPane;
    private JButton send;
    private JTextField msg;
    private JScrollBar scrollBar1;
    private JTextArea history;
    private JList users;
    private Client client;
    private DefaultListModel<String> usersModel = new DefaultListModel<>();
    
    public ClientWindow(Client client) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(send);

        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                client.sendMessage(msg.getText());
                msg.setText("");
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        users.setModel(usersModel);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    
    public void addMessage(String content) {
        history.append(content + "\n");
    }
    
    public void addUser(String content) {
    	usersModel.addElement(content);
    }
    
    public void removeUser(String content) {
    	//TODO: проверить сравнимость String
        usersModel.removeElement(content);
    }
}
