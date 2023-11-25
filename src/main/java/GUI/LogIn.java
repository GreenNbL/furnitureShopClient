package GUI;

import GUI.AdminGUI.AdminWindow;
import GUI.CustomerGUI.CustomerWindow;
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

public class LogIn extends JFrame{
    private JTextField loginField;
    private JPasswordField passwordField;
    private JPanel LogInPanel;
    private JButton signUpButton;
    private JButton enterButton;
    ObjectOutputStream coos;
    ObjectInputStream cois;

    public LogIn(ObjectOutputStream coos, ObjectInputStream cois)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(LogInPanel);
        setLocationRelativeTo(null);
        setSize(450,150);
        signUpButton.addActionListener(new signUpAction());
        enterButton.addActionListener(new enterAction());
        this.addWindowListener(new WindowClosing());
    }
    private class WindowClosing extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e) {
            //super.windowClosing(e);
            //setVisible(false);
            Client.closeApp();
            System.exit(0);
        }
    }
    private class enterAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            User user=new User();
            User userDB=new User();
            user.setLogin(loginField.getText());
            user.setPassword(String.valueOf(passwordField.getText().hashCode()));
            try {
                coos.writeObject("LogIn");
                coos.writeObject(user);
                userDB=(User)cois.readObject();
                if(userDB==null) {
                    JOptionPane.showMessageDialog(null, "Нет такого пользователя. Повторите попытку или зарегистрируйтесь.");
                    passwordField.setText("");
                }
                else
                {
                    if(userDB.getRole().equals("admin")) {
                        AdminWindow admin = new AdminWindow(coos, cois);
                        setVisible(false);
                    }
                    else {
                        CustomerWindow customerWindow = new CustomerWindow(coos, cois,userDB);
                        setVisible(false);
                    }

                }
                    //System.out.println("vse ok");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private class signUpAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            SignUp signUpWindow =new SignUp(coos,cois);
        }
    }

}
