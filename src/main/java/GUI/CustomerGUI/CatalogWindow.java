package GUI.CustomerGUI;

import client.Client;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class CatalogWindow extends JFrame {

    private JPanel catalogPanel;
    private JComboBox catalogComboBox;
    private JSpinner amountSpinner;
    private JCheckBox noCheckBox;
    private JCheckBox yesCheckBox;
    private JButton confirmButton;
    private JButton backButton;
    private JTextField adressField;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private List<Furniture> furnitures;
    private List<Order> orders;
    private User user;

    public CatalogWindow(ObjectOutputStream coos, ObjectInputStream cois, List<Order> orders,User user)
    {
        this.coos=coos;
        this.cois=cois;
        this.orders=orders;
        this.user=user;
        setVisible(true);
        setContentPane(catalogPanel);
        setLocationRelativeTo(null);
        setSize(700,200);
        furnitures=new ArrayList<Furniture>();
        this.addWindowListener(new WindowClosing());
        findAllFurnitures();
        for (Furniture furniture: furnitures) {
            if (furniture.getAmounStock() > 0) {
                catalogComboBox.addItem(furniture.getNameFurniture() + " " + furniture.getPrice() + " руб. " + furniture.getProvider().getCountry() + " " + furniture.getProvider().getCompany());

            }
        }
        noCheckBox.setSelected(true);
        adressField.setEditable(false);
        noCheckBox.addActionListener(new noChoice());
        yesCheckBox.addActionListener(new yesChoice());
        backButton.addActionListener(new BackAction());
        confirmButton.addActionListener(new ConfirmAction());
        System.out.println(user.getIdUser());
    }
    private class ConfirmAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Order order=new Order();
            order.setFurniture(furnitures.get(catalogComboBox.getSelectedIndex()));
            order.setUser(user);
            order.setAmount(Integer.valueOf(amountSpinner.getValue().toString()));
            order.setTotalCost(order.getAmount()*order.getFurniture().getPrice());
            LocalDate currentDateOrder = LocalDate.now();
            order.setDateOrder(Date.valueOf(currentDateOrder));
            if(yesCheckBox.isSelected()) {
                Delivery delivery=new Delivery();
                LocalDate currentDate = LocalDate.now();
                delivery.setDateDelivery(Date.valueOf(currentDate.plusDays(2)));
                delivery.setAdress(adressField.getText());
                delivery.setStatus("доставляется");
                order.setDelivery(delivery);
            }
            System.out.println(order.getUser().getIdUser());
            orders.add(order);
            System.out.println(orders);
            CustomerWindow customerWindow =new CustomerWindow(coos,cois,user,orders);
            setVisible(false);
        }
    }
    private void findAllFurnitures()
    {
        try {
            coos.writeObject("GetAllFurnitures");
            this.furnitures=(List<Furniture>)cois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void createUIComponents() {
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        this.amountSpinner = new JSpinner(model);
    }

    private class WindowClosing extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e) {
            Client.closeApp();
            System.exit(0);
        }
    }
    private class BackAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            setVisible(false);
            CustomerWindow customerWindow =new CustomerWindow(coos,cois,user,orders);
        }
    }
    private class yesChoice implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(yesCheckBox.isSelected()) {
                noCheckBox.setSelected(false);
                adressField.setEditable(true);
            }
            else {
                noCheckBox.setSelected(true);
                adressField.setEditable(false);
            }
        }
    }
    private class noChoice implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(noCheckBox.isSelected()) {
                yesCheckBox.setSelected(false);
                adressField.setEditable(false);
            }
            else {
                yesCheckBox.setSelected(true);
                adressField.setEditable(true);
            }
        }
    }
}
