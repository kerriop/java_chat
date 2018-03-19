package com.dima.server;

import com.dima.Main;
import com.dima.Utils;
import com.dima.message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Слушатель сервера
 */
public class Server implements Runnable {
    public List<ClientHandler> clients = new ArrayList<>();

    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(Main.PORT);
        } catch (IOException e) {
            Utils.showError("Не удаётся слушать порт " + Main.PORT);
            System.exit(-1);
            return;
        }

        Socket socket;
        while (true) {
            System.out.println("Ожидание клиента...");
            try {
                socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(this, socket);
                clients.add(client);
                new Thread(client).start();
            } catch (IOException ioe) {
                System.out.println("Не удаётся принять клиента");
            }
            System.out.println("Клиент подключился");
        }
    }
	
	/**
	 * Обработка присоединения клиента
	 * @param source источник события
	 * @param login его логин
	 */
	public void userConnected(ClientHandler source, String login) {
	    Message message = new Message(Message.SERVER_CLIENT_ADD_USER, login);
	    for (ClientHandler client : clients) {
		    if (client != source)
	    	    client.sendCustomMessage(message);
	    }
	    
	    for (ClientHandler client : clients) {
	    	if (client != source)
	    		source.sendCustomMessage(new Message(Message.SERVER_CLIENT_ADD_USER, client.getLogin()));
	    }
    }
	
	/**
	 * Обработка отсоединения клиента
	 * @param login его логин
	 */
	public void userDisconnected(String login) {
		Message message = new Message(Message.SERVER_CLIENT_REMOVE_UESR, login);
		for (ClientHandler client : clients)
			client.sendCustomMessage(message);
	}
	
	/**
	 * Отправка сообщения всем пользователям чата
	 * @param login логин
	 * @param text сообщение
	 */
    public void sendMessageToAll(String login, String text) {
    	String content = String.format(
    			"[%s] %s",
			    login,
			    text
	    );
	    for (ClientHandler client : clients)
		    client.sendMessage(content);
    }
}
