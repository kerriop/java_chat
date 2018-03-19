package com.dima.message;

import java.io.Serializable;

/**
 * Абстракция сообщения между клиентом и сервером
 */
public class Message implements Serializable {
	public static final int CLIENT_SERVER_PING = -1;
	
	//Сообщение сервера клиенту о присоединении пользователя
    public static final int SERVER_CLIENT_ADD_USER = 0;
    //Сообщение клиенту о удалении пользователя из чата
    public static final int SERVER_CLIENT_REMOVE_UESR = 1;
    //Сообщение клиента серверу о логине клиента
    public static final int CLIENT_SERVER_LOGIN = 2;
    //Пересылка чистого сообщения от клиента к серверу
    public static final int CLIENT_SERVER_MESSAGE = 3;
    //Пересылка чистого сообщения от сервера всем остальным клиентам
    public static final int SERVER_CLIENT_MESSAGE = 4;
    
    private final int type;
    private final String content;

    /**
     * Создать сообщение без тела
     * @param type тип сообщения
     */
    public Message(int type) {
        this(type, null);
    }
	
	/**
	 * Создать сообщение с типом и телом
	 * @param type тип сообщения
	 * @param content тело сообщения
	 */
	public Message(int type, String content) {
        this.type = type;
        this.content = content;
    }
	
	@Override
	public String toString() {
		return "Message{" +
				"type=" + type +
				", content='" + content + '\'' +
				'}';
	}
	
	/**
	 * Тип сообщения. Его числовой код. См. константы класса Message
	 */
	public int getType() {
        return this.type;
    }
	
	/**
	 * Строка, содержащаяся в теле сообщения
	 */
	public String getContent() {
        return this.content;
    }
}
