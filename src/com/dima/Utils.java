package com.dima;

import javax.swing.*;

/**
 * Всякие необходимые функции
 */
public class Utils {
	
	/**
	 * Вывести сообщение об ошибке Swing-алертом
	 * @param msg сообщение
	 */
	public static void showError(String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                "Ошибка",
                JOptionPane.ERROR_MESSAGE
        );
    }

}
