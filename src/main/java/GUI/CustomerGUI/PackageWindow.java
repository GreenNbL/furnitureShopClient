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
import java.util.List;

public class PackageWindow extends JFrame{
    private JPanel OrdesTablePanel;
    private JPanel root;
    private JScrollPane scroll;
    private JTable tableOrders;
    private JButton AddButton;
    private JButton DeleteButton;
    private JButton confermButton;
    private JButton backButton;
    private DefaultTableModel tableModel;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private List<Order> orders;
    private User user;
    public PackageWindow(ObjectOutputStream coos, ObjectInputStream cois,List<Order> orders,User user)
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
        DeleteButton.addActionListener(new DeleteAction());
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
    private class DeleteAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            TableModel model = tableOrders.getModel();
            orders.remove(tableOrders.getSelectedRow());
            PackageWindow packageWindow =new PackageWindow(coos,cois,orders,user);
            setVisible(false);
        }
    }
    private class confermAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(orders.get(0).getIdUser());
            try {
                if(orders.size()==0)
                    JOptionPane.showMessageDialog(null, "У вас пустая корзина! \nДобавьте мебель из каталога для того, чтобы выполнить заказ.");
                else {
                    System.out.println("AddOrder");
                    System.out.println("AddDelivery");
                    System.out.println("EditFurniture");
                    for(Order order:orders) {
                        order.getFurniture().setAmounStock(order.getFurniture().getAmounStock()-order.getAmount());
                        coos.writeObject("EditFurniture");
                        coos.writeObject(order.getFurniture());
                        if(order.getDelivery()!=null) {
                            coos.writeObject("AddDelivery");
                            coos.writeObject(order.getDelivery());
                        }
                        coos.writeObject("AddOrder");
                        coos.writeObject(order);
                    }
                    JOptionPane.showMessageDialog(null, "Ваш заказ принят!");
                }
                System.out.println(user.getIdUser());
                setVisible(false);
                CustomerWindow customerWindow = new CustomerWindow(coos, cois,user,orders);
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
        Object[] columnTitle = {"№","Мебель", "Доставка", "Количество","Общая стоимость ","Дата заказа"};
        tableModel = new DefaultTableModel(null, columnTitle);
        tableOrders.setModel(tableModel);
        tableModel.getDataVector().removeAllElements();
        System.out.println("Connected in Action_dialog");
            for(Order order:orders)
            {
                String delivery;
                if(order.getDelivery()!=null)
                    delivery="доставляется";
                else
                    delivery="самовывоз";
                String  furniture=order.getFurniture().getNameFurniture()+" "+order.getFurniture().getProvider().getCountry()+" "+order.getFurniture().getProvider().getCompany();
                Object[] data = {
                        order.getIdOrder()+1,
                        furniture,
                        delivery,
                        order.getAmount(),
                        order.getTotalCost(),
                        order.getDateOrder()
                };
                tableModel.addRow(data);
            }

    }
}
