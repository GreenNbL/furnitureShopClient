package GUI.CustomerGUI;

import client.Client;
import models.Order;
import models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomerWindow extends JFrame{
    private JButton catalogButton;
    private JPanel customerPanel;
    private JButton packageButton;
    private JButton yourOrdersButton;
    private JButton deliveryButton;
    private JLabel nameField;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private List<Order> orders;
    private User user;

    public CustomerWindow(ObjectOutputStream coos, ObjectInputStream cois,User user,List<Order> orders)
    {
        this.coos=coos;
        this.cois=cois;
        this.user=user;
        this.orders=orders;
        setVisible(true);
        setContentPane(customerPanel);
        setLocationRelativeTo(null);
        setSize(500,200);
        this.addWindowListener(new WindowClosing());
        catalogButton.addActionListener(new catalogAction());
        packageButton.addActionListener(new  packageAction());
        yourOrdersButton.addActionListener(new yourOrdersAction());
        System.out.println(user.getIdUser());

    }
    public CustomerWindow(ObjectOutputStream coos, ObjectInputStream cois,User user)
    {
        this.coos=coos;
        this.cois=cois;
        this.user=user;
        setVisible(true);
        setContentPane(customerPanel);
        setLocationRelativeTo(null);
        setSize(500,200);
        orders=new ArrayList<Order>();
        this.addWindowListener(new WindowClosing());
        catalogButton.addActionListener(new catalogAction());
        packageButton.addActionListener(new  packageAction());
        yourOrdersButton.addActionListener(new yourOrdersAction());
        System.out.println(user.getIdUser());

    }
    private class yourOrdersAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            PreviousOrdersWindow previousOrdersWindow =new PreviousOrdersWindow(coos,cois,orders,user);
            setVisible(false);
        }
    }
    private class packageAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            PackageWindow packageWindow =new PackageWindow(coos,cois,orders,user);
            setVisible(false);
        }
    }
    private class catalogAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            CatalogWindow catalogWindow =new CatalogWindow(coos,cois,orders,user);
            setVisible(false);
        }
    }
    private class WindowClosing extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e) {
            Client.closeApp();
            System.exit(0);
        }
    }

}
