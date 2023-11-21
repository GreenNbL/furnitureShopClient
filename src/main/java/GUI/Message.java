package GUI;

import client.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Message extends JFrame{
    private JPanel MessagePanel;
    private JTextArea messageArea;
    private JButton okeyButton;

    public Message(String message)
    {
        setVisible(true);
        setContentPane(MessagePanel);
        setLocationRelativeTo(null);
        setSize(600,150);
        messageArea.setText(message);
        okeyButton.addActionListener(new OkeyAction());
        this.addWindowListener(new WindowClosing());
    }
    private class WindowClosing extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }
    private class OkeyAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }
}
