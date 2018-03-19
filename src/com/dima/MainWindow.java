package com.dima;

import com.dima.client.Client;
import com.dima.server.Server;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JButton server;
    private JButton client;
    private JButton buttonOK;

    public MainWindow() {
        setTitle("Chat");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        server.addActionListener(e -> {
        	server.setEnabled(false);
            Server server = new Server();
            new Thread(server).start();
        });
        client.addActionListener(e -> {
            try {
            	client.setEnabled(false);
            	
            	String login = JOptionPane.showInputDialog("Ваш логин");
                Socket socket = new Socket("127.0.0.1", Main.PORT);
                Client clientObj = new Client(socket, login);
                
                ClientWindow clientWindow = new ClientWindow(clientObj);
	            clientWindow.pack();
	            clientWindow.setSize(400, 300);
	            clientWindow.setLocationByPlatform(true);
	            clientWindow.setVisible(true);
	            
	            clientObj.setWindow(clientWindow);
				
	            new Thread(clientObj).start();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });
    }
}
