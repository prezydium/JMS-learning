package org.prezydium;

import javax.jms.JMSException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class AppWindow {
    private JTextArea textArea1;
    private JPanel panel1;
    private JTextField textField1;
    private JButton disconnectButton;
    private JTextPane almostGgTextPane;


    public AppWindow() {
        JFrame frame = new JFrame();
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(300, 400));
        frame.setLocationRelativeTo(null);
        frame.setContentPane(panel1);
        frame.setVisible(true);
        textField1.requestFocus();

        textField1.addActionListener(actionEvent -> {
            if (textField1.getText() != null && !textField1.getText().isEmpty()) {
                try {
                    Producer.sendMessage(textField1.getText());
                    textField1.setText("");
                } catch (JMSException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        disconnectButton.addActionListener(actionEvent -> Consumer.setExit(true));
    }

    public void appendReceivedMessages(String text) {
        textArea1.append(text + "\n");
    }

}
