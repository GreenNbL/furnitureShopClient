package GUI;

import client.Client;
import models.User;
import Checkings.Checkings;
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
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
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
                user.setPassword(String.valueOf(passwordField.getText().hashCode()));
                user.setRole("customer");
                if(Checkings.ContainsNumber(surnameField))
                {
                    user.setSurname(surnameField.getText());
                }
                else
                {
                    surnameField.setText("");
                    JOptionPane.showMessageDialog(null,"Фамилия не может содержать цифры");
                    return;
                }
                if(Checkings.ContainsNumber(nameField))
                {
                    user.setName(nameField.getText());
                }
                else
                {
                    nameField.setText("");
                    JOptionPane.showMessageDialog(null,"Имя не может содержать цифры");
                    return;
                }
                if(Checkings.IsPhoneNumberCorrect(telNumberField))
                {
                    user.setTel_number(telNumberField.getText());
                }
                else
                {
                    telNumberField.setText("");
                    JOptionPane.showMessageDialog(null,"Номер телефона введен не верно");
                    return;
                }

                try {
                    coos.writeObject("SignUp");
                    coos.writeObject(user);
                    if(cois.readObject().toString().equals("there is such user")){
                        JOptionPane.showMessageDialog(null, "Такой пользователь уже существует. Попробуйте войти в аккаунт, либо создайте нового.");
                        passwordField.setText("");
                    }else {
                        LogIn logIn=new LogIn(coos,cois);
                        JOptionPane.showMessageDialog(null, "Вы зарегистрировались успешно!");
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
