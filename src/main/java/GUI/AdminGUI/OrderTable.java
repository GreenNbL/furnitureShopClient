package GUI.AdminGUI;

import models.Order;

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

public class OrderTable extends JFrame{
    private JPanel OrdesTablePanel;
    private JPanel root;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JTable tableOrders;
    private JButton AddButton;
    private JButton DeleteButton;
    private JButton EditButton;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    public OrderTable(ObjectOutputStream coos, ObjectInputStream cois)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(OrdesTablePanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        AddButton.addActionListener(new AddAction());
        EditButton.addActionListener(new EditAction());
        DeleteButton.addActionListener(new DeleteAction());
        ShowData();
    }
    private class DeleteAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            TableModel model = tableOrders.getModel();
            Order order=new Order();
            System.out.println("FindOrderById");
            try {
                coos.writeObject("FindOrderById");
                coos.writeObject(Integer.valueOf(model.getValueAt(tableOrders.getSelectedRow(),0).toString()));
                order=(Order)cois.readObject();
                coos.writeObject("DeleteOrder");
                coos.writeObject(order);
                setVisible(false);
                OrderTable orderTable = new OrderTable(coos, cois);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class EditAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            TableModel model = tableOrders.getModel();
            Order order=new Order();
            System.out.println("FindOrderById");
            try {
                coos.writeObject("FindOrderById");
                coos.writeObject(Integer.valueOf(model.getValueAt(tableOrders.getSelectedRow(),0).toString()));
                order=(Order)cois.readObject();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            //AddEditUserTable addEditUserTable =new AddEditUserTable(coos,cois, tableOrders,user);
            AddEditOrder addEditOrder =new AddEditOrder(coos,cois,tableOrders,order);
            setVisible(false);
        }
    }
    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddEditOrder addEditOrder =new AddEditOrder(coos,cois,"Добавление заказа");
            setVisible(false);
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
        Object[] columnTitle = {"id_order","id_user", "id_furniture", "id_delivery", "Количество","Общая стоимость ","Дата заказа"};
        tableModel = new DefaultTableModel(null, columnTitle);
        tableOrders.setModel(tableModel);
        tableModel.getDataVector().removeAllElements();
        System.out.println("Connected in Action_dialog");
        try {
            coos.writeObject("GetAllOrders");
            List<Order> orders=new ArrayList<Order>();
            orders=(List<Order>)cois.readObject();
            System.out.println(orders);
            for(Order order:orders)
            {
                String stm;
                if(order.getDelivery()==null)
                    stm="-";
                else
                    stm=String.valueOf(order.getIdDelivery());
                Object[] data = {
                        order.getIdOrder(),
                        order.getIdUser(),
                        order.getIdFurniture(),
                        stm,
                        order.getAmount(),
                        order.getTotalCost(),
                        order.getDateOrder()

                };
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
