package GUI.AdminGUI;

import models.Delivery;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;

public class AddEditDelivery extends JFrame{
    private JPanel AddEditPanel;
    private JPanel AddEditPanel1;
    private JButton addButton;
    private JLabel nameOfActionLabel;
    private JButton backButton;
    private JCheckBox completedCheckBox;
    private JCheckBox performedCheckBox;
    private JLabel statusField;
    private JLabel dateField;
    private JTextField dateField1;
    private Delivery delivery;
    private JTable tableDeliveries;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private String status;
    public AddEditDelivery(ObjectOutputStream coos, ObjectInputStream cois, String nameOfAction)
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
        completedCheckBox.addActionListener(new CompletedChoice());
        performedCheckBox.addActionListener(new PerformedChoice());
        backButton.addActionListener(new backAction());
    }
    public AddEditDelivery(ObjectOutputStream coos, ObjectInputStream cois, JTable tableDeliveries,Delivery delivery)
    {
        this.coos=coos;
        this.cois=cois;
        this.tableDeliveries=tableDeliveries;
        this.delivery=delivery;
        setVisible(true);
        setContentPane(AddEditPanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        nameOfActionLabel.setText("Редактирование доставки");
        addButton.setText("Редактировать");
        addButton.addActionListener(new EditAction());
        completedCheckBox.addActionListener(new CompletedChoice());
        performedCheckBox.addActionListener(new PerformedChoice());
        backButton.addActionListener(new backAction());
        dateField1.setText(delivery.getDateDelivery().toString());
        if(delivery.getStatus().equals("доставляется"))
        {
            performedCheckBox.setSelected(true);
            this.status="доставляется";
        }
        else
        {
            completedCheckBox.setSelected(true);
            this.status="завершена";
        }
    }
    private class EditAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            TableModel model =  tableDeliveries.getModel();
            delivery.setDateDelivery(Date.valueOf(dateField1.getText()));
            delivery.setStatus(status);
            if(performedCheckBox.isSelected())
                delivery.setStatus("доставляется");
            else
                delivery.setStatus("завершена");
            try {
                coos.writeObject("EditDelivery");
                coos.writeObject(delivery);
                DeliveryTable usersTable = new DeliveryTable(coos, cois);
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
            Delivery delivery=new Delivery();
            delivery.setStatus(status);
            delivery.setDateDelivery(Date.valueOf(dateField1.getText()));
            try {
                setVisible(false);
                coos.writeObject("AddDelivery");
                coos.writeObject(delivery);
                JOptionPane.showMessageDialog(null, "Вы добавили доставку успешно!");
                DeliveryTable usersTable = new DeliveryTable(coos, cois);

            } catch (IOException ex) {
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
    private class PerformedChoice implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(performedCheckBox.isSelected()) {
                completedCheckBox.setSelected(false);
                status="доставляется";
            }
            else {
                completedCheckBox.setSelected(true);
                status = "завершена";
            }
        }
    }
    private class CompletedChoice implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(completedCheckBox.isSelected()) {
                performedCheckBox.setSelected(false);
                status="завершена";
            }
            else {
                performedCheckBox.setSelected(true);
                status = "доставляется";
            }

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
