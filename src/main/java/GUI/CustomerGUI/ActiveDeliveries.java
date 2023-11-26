package GUI.CustomerGUI;

import models.Order;
import models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ActiveDeliveries extends JFrame{
    private JPanel OrdesTablePanel;
    private JPanel root;
    private JScrollPane scroll;
    private JTable tableOrders;
    private JButton confermButton;
    private JButton backButton;
    private DefaultTableModel tableModel;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private List<Order> orders;
    private User user;
    public ActiveDeliveries(ObjectOutputStream coos, ObjectInputStream cois,User user)
    {
        System.out.println("чунга чанга");
        this.coos=coos;
        this.cois=cois;
        this.user=user;
        setVisible(true);
        setContentPane(OrdesTablePanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        confermButton.addActionListener(new confermAction());
        backButton.addActionListener(new BackAction());
        ShowData();
        System.out.println(user.getIdUser());
    }
    public ActiveDeliveries(ObjectOutputStream coos, ObjectInputStream cois,List<Order>orders, User user)
    {
        this.coos=coos;
        this.cois=cois;
        this.user=user;
        this.orders=orders;
        setVisible(true);
        setContentPane(OrdesTablePanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        confermButton.addActionListener(new confermAction());
        backButton.addActionListener(new BackAction());
        ShowData();
        System.out.println(user.getIdUser());
    }
    private class BackAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            setVisible(false);
            CustomerWindow customerWindow = new CustomerWindow(coos, cois,user,orders);

        }
    }
    private class confermAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Order order=new Order();
               // System.out.println(tableOrders.getSelectedRow());
                order=orders.get(tableOrders.getSelectedRow());
               // System.out.println(order);
                order.getDelivery().setStatus("завершена");
                System.out.println("EditDelivery");
                coos.writeObject("EditDelivery");
                coos.writeObject(order.getDelivery());
                JOptionPane.showMessageDialog(null, "Спасибо, что подтвердили заказ!");
                setVisible(false);
                ActiveDeliveries activeDeliveries = new ActiveDeliveries(coos, cois,orders,user);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class WindowClosing extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e) {
            //Client.closeApp();
            //System.exit(0);
            setVisible(false);
        }
    }
    public void ShowData() {
        Object[] columnTitle = {"Мебель", "Доставка", "Количество","Общая стоимость ","Дата заказа"};
        tableModel = new DefaultTableModel(null, columnTitle);
        tableOrders.setModel(tableModel);
        tableModel.getDataVector().removeAllElements();
        System.out.println("Connected in Action_dialog");
        try {
            coos.writeObject("FindActiveOrders");
            List<Order>orders=new ArrayList<Order>();
            orders=(List<Order>)cois.readObject();
            this.orders=orders;
            for(Order order:orders)
            {
                    if(order.getIdUser()==user.getIdUser()) {
                        //  System.out.println( order.getDelivery().getIdDelivery());
                        String furniture = order.getFurniture().getNameFurniture() + " " + order.getFurniture().getProvider().getCountry() + " " + order.getFurniture().getProvider().getCompany();
                        Object[] data = {
                                furniture,
                                order.getDelivery().getStatus(),
                                order.getAmount(),
                                order.getTotalCost(),
                                order.getDateOrder()
                        };
                        tableModel.addRow(data);
                    }


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
