package com.dima.client;

import com.dima.ClientWindow;
import com.dima.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Клиентский обработчик сети
 */
public class Client implements Runnable {
	private String login;
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ClientWindow window;
	
	public Client(Socket socket, String login) throws IOException {
		this.socket = socket;
		this.login = login;
		this.output = new ObjectOutputStream(socket.getOutputStream());
		this.output.flush();
		this.input = new ObjectInputStream(socket.getInputStream());
	}
	
	/**
	 * Установить привязанное окно чата
	 * @param window окно чата
	 */
	public void setWindow(ClientWindow window) {
		this.window = window;
	}
	
	/**
	 * Отправить сообщение в чат
	 * @param text текст сообщения
	 */
	public void sendMessage(String text) {
		Message message = new Message(Message.CLIENT_SERVER_MESSAGE, text);
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
			//Посылаем свой логин серверу
			Message message = new Message(Message.CLIENT_SERVER_LOGIN, login);
			output.writeObject(message);
			output.flush();
			
			//Читаем сообщения сервера
			while (true) {
				message = (Message) input.readObject();
				switch (message.getType()) {
					case Message.SERVER_CLIENT_MESSAGE:
						window.addMessage(message.getContent());
						break;
					case Message.SERVER_CLIENT_ADD_USER:
						window.addUser(message.getContent());
						break;
					case Message.SERVER_CLIENT_REMOVE_UESR:
						window.removeUser(message.getContent());
						break;
					default:
						System.out.println("Unknown message: " + message);
				}
				//System.out.println(message);
			}
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (IOException ioe) {
			tryClose();
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Попытаться закрыть стримы и сокет после ошибки
	 */
	private void tryClose() {
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
