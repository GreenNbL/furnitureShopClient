package GUI.AdminGUI;

import models.Delivery;

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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DeliveryTable extends JFrame {
    private DefaultTableModel tableModel;
    private JPanel deliveriesTablePanel;
    private JPanel root;
    private JScrollPane scroll;
    private JTable tableDeliveries;
    private JButton AddButton;
    private JButton DeleteButton;
    private JButton EditButton;
    private JPanel orderTablePanel;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    public DeliveryTable(ObjectOutputStream coos, ObjectInputStream cois)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(deliveriesTablePanel);
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

            TableModel model = tableDeliveries.getModel();
            Delivery delivery=new Delivery();
            delivery.setIdDelivery(Integer.valueOf(model.getValueAt(tableDeliveries.getSelectedRow(),0).toString()));

            if(model.getValueAt(tableDeliveries.getSelectedRow(),1)!=null)
                delivery.setStatus(model.getValueAt(tableDeliveries.getSelectedRow(),1).toString());

            if(model.getValueAt(tableDeliveries.getSelectedRow(),2)!=null)
                delivery.setDateDelivery(Date.valueOf(model.getValueAt(tableDeliveries.getSelectedRow(),2).toString()));
            try {
                coos.writeObject("DeleteDelivery");
                coos.writeObject(delivery);
                setVisible(false);
                DeliveryTable usersTable = new DeliveryTable(coos, cois);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class EditAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            TableModel model = tableDeliveries.getModel();
            Delivery delivery=new Delivery();
            delivery.setIdDelivery(Integer.valueOf(model.getValueAt(tableDeliveries.getSelectedRow(),0).toString()));

            if(model.getValueAt(tableDeliveries.getSelectedRow(),1)!=null)
                delivery.setStatus(model.getValueAt(tableDeliveries.getSelectedRow(),1).toString());

            if(model.getValueAt(tableDeliveries.getSelectedRow(),2)!=null)
                delivery.setDateDelivery(Date.valueOf(model.getValueAt(tableDeliveries.getSelectedRow(),2).toString()));

            AddEditDelivery addEditDelivery=new AddEditDelivery(coos,cois, tableDeliveries,delivery);
            setVisible(false);
        }
    }
    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddEditDelivery addEditDelivery =new AddEditDelivery(coos,cois,"Добавление доставки");
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
        Object[] columnTitle = {"id_delivery","статус", "дата доставки"};
        tableModel = new DefaultTableModel(null, columnTitle);
        tableDeliveries.setModel(tableModel);
        tableModel.getDataVector().removeAllElements();
        System.out.println("Connected in Action_dialog");
        try {
            coos.writeObject("GetAllDeliveries");
            List<Delivery> deliveries=new ArrayList<Delivery>();
            deliveries=(List<Delivery>)cois.readObject();
            System.out.println(deliveries);
            for(Delivery delivery:deliveries)
            {
                System.out.println( delivery.getDateDelivery());
                Object[] data = {
                        delivery.getIdDelivery(),
                        delivery.getStatus(),
                        delivery.getDateDelivery()
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
