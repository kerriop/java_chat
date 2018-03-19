package com.dima.message;

/**
 * Исключение, возникающее при неверном типе пришедшего сообщения
 * при данном состоянии слушателя сообщений
 * Например, приход текстового сообщения до логина клиента
 */
public class IllegalMessageException extends Exception {
	
	public IllegalMessageException() {
		super();
	}
	
	public IllegalMessageException(String message) {
		super(message);
	}
}
