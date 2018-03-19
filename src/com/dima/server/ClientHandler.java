package com.dima.server;

import com.dima.message.IllegalMessageException;
import com.dima.message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Слушатель клиента на серверной стороне
 */
public class ClientHandler implements Runnable {
	private String login = null;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Server server;

    public ClientHandler(Server server, Socket socket) throws IOException {
    	this.server = server;
        this.socket = socket;
        this.output = new ObjectOutputStream(socket.getOutputStream());
	    this.output.flush();
        this.input = new ObjectInputStream(socket.getInputStream());
    }
	
	public String getLogin() {
		return login;
	}
	
	/**
	 * Отправить сообщение в чат
	 * @param text текст сообщения
	 */
	public void sendMessage(String text) {
		Message message = new Message(Message.SERVER_CLIENT_MESSAGE, text);
		sendCustomMessage(message);
	}
	
	/**
	 * Отправить абстрактное сообщение
	 * @param message сообщение
	 */
	public void sendCustomMessage(Message message) {
		try {
			output.writeObject(message);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    @Override
    public void run() {
	    try {
		    Message message = (Message) input.readObject();
		    if (message.getType() == Message.CLIENT_SERVER_LOGIN) {
		    	login = message.getContent();
			    System.out.println(String.format(
			    		"Пользователь %s подключился к чату",
					    login
			    ));
			    server.userConnected(this, login);
		    } else {
		    	throw new IllegalMessageException("Первое сообщение клиента должно быть логином! " + message);
		    }
	    } catch (IOException e) {
	    	tryClose();
	    	e.printStackTrace();
	    	return;
	    } catch (ClassNotFoundException e) {
		    tryClose();
		    e.printStackTrace();
		    return;
	    } catch (IllegalMessageException e) {
		    e.printStackTrace();
		    tryClose();
		    return;
	    }
	
	    //цикл хандлера
        try {
            while (true) {
            	Message message = (Message) input.readObject();
            	switch (message.getType()) {
		            case Message.CLIENT_SERVER_MESSAGE:
		            	server.sendMessageToAll(login, message.getContent());
		            	break;
					default:
						System.out.println("Unknown message: " + message);
	            }
            }
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (IOException ioe) {
            tryClose();
        }
    }
	
	/**
	 * Попытаться закрыть стримы и сокет после ошибки
	 */
	private void tryClose() {
		if (login != null) {
			server.userDisconnected(login);
		}
		try {
			input.close();
		} catch (Exception e) {}
		try {
			output.close();
		} catch (Exception e) {}
		try {
			socket.close();
		} catch (Exception e) {}
	}
}
