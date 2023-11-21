package GUI.AdminGUI;

import models.User;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddEditUserTable extends JFrame {
    private JPanel SignUpPanel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField surnameField;
    private JTextField nameField;
    private JTextField telNumberField;
    private JButton addButton;
    private JButton backButton;
    private JCheckBox customerCheckBox;
    private JCheckBox adminCheckBox;
    private JPanel AddEditPanel;
    private JLabel nameOfActionLabel;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private String role;
    private JTable tableUsers;
    private User user;
    public AddEditUserTable(ObjectOutputStream coos, ObjectInputStream cois,String nameOfAction)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(AddEditPanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        nameOfActionLabel.setText(nameOfAction);
        addButton.setText(nameOfAction);
        addButton.addActionListener(new AddAction());
        customerCheckBox.addActionListener(new CustomerChoice());
        adminCheckBox.addActionListener(new AdminChoice());
        backButton.addActionListener(new backAction());
    }
    public AddEditUserTable(ObjectOutputStream coos, ObjectInputStream cois, JTable tableUsers,User user)
    {
        this.coos=coos;
        this.cois=cois;
        this.tableUsers=tableUsers;
        this.user=user;
        setVisible(true);
        setContentPane(AddEditPanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        nameOfActionLabel.setText("Редактирование пользователя");
        addButton.setText("Редактировать");
        addButton.addActionListener(new EditAction());
        customerCheckBox.addActionListener(new CustomerChoice());
        adminCheckBox.addActionListener(new AdminChoice());
        backButton.addActionListener(new backAction());
        loginField.setText(user.getLogin());
        surnameField.setText(user.getSurname());
        nameField.setText(user.getName());
        telNumberField.setText(user.getTel_number());
        if(user.getRole().equals("admin"))
        {
            adminCheckBox.setSelected(true);
            this.role="admin";
        }
        else
        {
            customerCheckBox.setSelected(true);
            this.role="customer";
        }
    }
    private class EditAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            TableModel model =  tableUsers.getModel();
            user.setLogin(loginField.getText());
            user.setPassword(passwordField.getText());
            user.setRole(role);
            user.setSurname(surnameField.getText());
            user.setName(nameField.getText());
            user.setTel_number(telNumberField.getText());
            try {
                coos.writeObject("EditUser");
                coos.writeObject(user);
                UsersTable usersTable = new UsersTable(coos, cois);
                JOptionPane.showMessageDialog(null, "Изменения проименены успешно!");
                setVisible(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            User user = new User();
            user.setLogin(loginField.getText());
            user.setPassword(passwordField.getText());
            user.setRole(role);
            user.setSurname(surnameField.getText());
            user.setName(nameField.getText());
            user.setTel_number(telNumberField.getText());
            try {
                setVisible(false);
                coos.writeObject("SignUp");
                coos.writeObject(user);
                if(cois.readObject().toString().equals("there is such user")){
                    JOptionPane.showMessageDialog(null, "Такой пользователь уже существует. Попробуйте войти в аккаунт, либо создайте нового.");
                }else {
                    //Message message = new Message("Вы зарегистрировались успешно!");
                    JOptionPane.showMessageDialog(null, "Вы зарегистрировались успешно!");
                    UsersTable usersTable = new UsersTable(coos, cois);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class backAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
           setVisible(false);
        }
    }
    private class CustomerChoice implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            role="customer";
            if(adminCheckBox.isSelected())
                adminCheckBox.setSelected(false);
        }
    }
    private class AdminChoice implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            role="admin";
            if(customerCheckBox.isSelected())
                customerCheckBox.setSelected(false);
        }
    }
    private class WindowClosing extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }
}
