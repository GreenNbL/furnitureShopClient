package GUI;

import models.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEditOrder extends JFrame{
    private JPanel orderAddEditPanel;
    private JTextField dateField;
    private JTextField amountField;
    private JButton addButton;
    private JLabel nameOfActionLabel;
    private JButton backButton;
    private JPanel SignUpPanel;
    private JPanel orderPanel;
    private JLabel deliveryField;
    private JLabel dateField2;
    private JLabel amountField2;
    private JLabel furnitureField;
    private JLabel clientField;
    private JComboBox deliveryComboBox;
    private JComboBox furnitureComboBox;
    private JComboBox usersComboBox;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private String role;
    private JTable tableOrders;
    private Order order;
    public AddEditOrder(ObjectOutputStream coos, ObjectInputStream cois,String nameOfAction)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(orderAddEditPanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        nameOfActionLabel.setText(nameOfAction);
        addButton.setText(nameOfAction);
        addButton.addActionListener(new AddAction());
//        customerCheckBox.addActionListener(new CustomerChoice());
//        adminCheckBox.addActionListener(new AdminChoice());
        backButton.addActionListener(new backAction());
        try {
            coos.writeObject("GetAllUsers");
            List<User> users=new ArrayList<User>();
            users=(List<User>)cois.readObject();
            for (User user: users) {
                usersComboBox.addItem(user.toString());
            }

            coos.writeObject("GetAllFurnitures");
            List<Furniture> furnitures=new ArrayList<Furniture>();
            furnitures=(List<Furniture>)cois.readObject();
            for (Furniture furniture: furnitures) {
                furnitureComboBox.addItem(furniture.toString());
            }

            coos.writeObject("GetAllDeliveries");
            List<Delivery> deliveries=new ArrayList<Delivery>();
            deliveries=(List<Delivery>)cois.readObject();
            for (Delivery delivery: deliveries) {
                deliveryComboBox.addItem(delivery.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public AddEditOrder(ObjectOutputStream coos, ObjectInputStream cois, JTable tableOrders,Order order)
    {
        this.coos=coos;
        this.cois=cois;
        this.tableOrders=tableOrders;
        this.order=order;
        setVisible(true);
        setContentPane(orderAddEditPanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        nameOfActionLabel.setText("Редактирование заказа");
        addButton.setText("Редактировать");
        addButton.addActionListener(new EditAction());
        backButton.addActionListener(new backAction());
//        loginField.setText(user.getLogin());
//        surnameField.setText(user.getSurname());
//        nameField.setText(user.getName());
//        telNumberField.setText(user.getTel_number());
//        if(user.getRole().equals("admin"))
//        {
//            adminCheckBox.setSelected(true);
//            this.role="admin";
//        }
//        else
//        {
//            customerCheckBox.setSelected(true);
//            this.role="customer";
//        }
    }
    private class EditAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
//            TableModel model =  tableUsers.getModel();
//            user.setLogin(loginField.getText());
//            user.setPassword(passwordField.getText());
//            user.setRole(role);
//            user.setSurname(surnameField.getText());
//            user.setName(nameField.getText());
//            user.setTel_number(telNumberField.getText());
//            try {
//                coos.writeObject("EditUser");
//                coos.writeObject(user);
//                UsersTable usersTable = new UsersTable(coos, cois);
//                JOptionPane.showMessageDialog(null, "Изменения проименены успешно!");
//                setVisible(false);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
        }
    }
    private int FindFirstNumberInString(String str) {
        Pattern pattern = Pattern.compile("\\d+"); // Регулярное выражение для поиска чисел
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            String number = matcher.group();
            System.out.println( number);// Получение первого попавшегося числа
            return Integer.parseInt(number); // Преобразование найденной строки в число
            //System.out.println("Найденное число: " + firstNumber);
        }
        return 0;

    }
    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            Order order =new Order();
            User user=new User();
            Furniture furniture=new Furniture();
            Delivery delivery=new Delivery();
            System.out.println(dateField.getText());
            order.setDateOrder(Date.valueOf(dateField.getText()));
            order.setAmount(Integer.valueOf(amountField.getText()));
            try {
                System.out.println("FindDeliveryById");
                coos.writeObject("FindDeliveryById");
                coos.writeObject(FindFirstNumberInString(deliveryComboBox.getSelectedItem().toString()));
                delivery=(Delivery)cois.readObject();
                order.setDelivery(delivery);

                System.out.println("FindUserById");
                coos.writeObject("FindUserById");
                coos.writeObject(FindFirstNumberInString(usersComboBox.getSelectedItem().toString()));
                user=(User)cois.readObject();
                order.setUser(user);

                System.out.println("FindFurnitureById");
                coos.writeObject("FindFurnitureById");
                coos.writeObject(FindFirstNumberInString(furnitureComboBox.getSelectedItem().toString()));
                furniture=(Furniture)cois.readObject();
                order.setFurniture(furniture);

                System.out.println("AddOrder");
                setVisible(false);
                coos.writeObject("AddOrder");
                coos.writeObject(order);
                JOptionPane.showMessageDialog(null, "Вы добавили заказ успешно!");
                OrderTable orderTable = new OrderTable(coos, cois);

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

    private class WindowClosing extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    }
}
