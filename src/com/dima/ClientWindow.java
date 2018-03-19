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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        users.setModel(usersModel);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
	
	/**
	 * Добавить сообщение в историю чата
	 * @param message сообщение
	 */
	public void addMessage(String message) {
        history.append(message + "\n");
    }
	
	/**
	 * Добавить пользователя в список пользователей
	 * @param login логин пользователя
	 */
	public void addUser(String login) {
    	usersModel.addElement(login);
    }
	
	/**
	 * Удалить пользователя из списка пользователей
	 * @param login логин пользователя
	 */
	public void removeUser(String login) {
        usersModel.removeElement(login);
    }
}
