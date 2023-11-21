package GUI;

import client.Client;
import models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SignUp extends JFrame {
    private JPanel SignUpPanel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField surnameField;
    private JTextField nameField;
    private JTextField telNumberField;
    private JButton signUpButton;
    private JButton backButton;
    ObjectOutputStream coos;
    ObjectInputStream cois;
    public SignUp(ObjectOutputStream coos, ObjectInputStream cois)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(SignUpPanel);
        setLocationRelativeTo(null);
        setSize(450,300);
        signUpButton.addActionListener(new SignUpAction());
        this.addWindowListener(new WindowClosing());
        backButton.addActionListener(new backAction());
    }
    private class backAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            LogIn logIn=new LogIn(coos,cois);
        }
    }

    private class WindowClosing extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e) {
           // LogIn logIn=new LogIn(coos,cois);
            Client.closeApp();
            System.exit(0);
        }
    }
    private class SignUpAction  implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setLogin(loginField.getText());
                user.setPassword(passwordField.getText());
                user.setRole("customer");
                user.setSurname(surnameField.getText());
                user.setName(nameField.getText());
                user.setTel_number(telNumberField.getText());
                try {
                    coos.writeObject("SignUp");
                    coos.writeObject(user);
                    if(cois.readObject().toString().equals("there is such user")){
                        //setEnabled(false);
                        //Message message = new Message("Такой пользователь уже существует." +
                          //      " Попробуйте войти в аккаунт, либо создайте нового.");
                        JOptionPane.showMessageDialog(null, "Такой пользователь уже существует. Попробуйте войти в аккаунт, либо создайте нового.");
                    }else {
                        LogIn logIn=new LogIn(coos,cois);
                        Message message = new Message("Вы зарегистрировались успешно!");
                        setVisible(false);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

        }
    }

}
