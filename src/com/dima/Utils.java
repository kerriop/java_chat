package com.dima;

import javax.swing.*;

public class Utils {

    public static void showError(String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                "Ошибка",
                JOptionPane.ERROR_MESSAGE
        );
    }

}
