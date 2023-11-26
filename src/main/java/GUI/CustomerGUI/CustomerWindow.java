package GUI.CustomerGUI;

import File.TextFileWriter;
import GUI.LogIn;
import GUI.Statistics.BarCharts.CircleChart;
import client.Client;
import models.Order;
import models.User;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
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
    private JButton fileWriteButton;
    private JButton diagramButton;
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
        deliveryButton.addActionListener(new activeDeliveriesAction());
        nameField.setText(user.getSurname()+" "+user.getName());
        fileWriteButton.addActionListener(new WriteFile());
        diagramButton.addActionListener(new ShowDiagram());

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
        nameField.setText(user.getSurname()+" "+user.getName());
        deliveryButton.addActionListener(new activeDeliveriesAction());
        fileWriteButton.addActionListener(new WriteFile());
        diagramButton.addActionListener(new ShowDiagram());

    }
    private class ShowDiagram implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("FindOrderByUserId");
            try {
                coos.writeObject("FindOrderByUserId");
                coos.writeObject(user.getIdUser());
                List<Order>orders=new ArrayList<Order>();
                orders=(List<Order>)cois.readObject();
                CircleChart demo = new CircleChart("Круговая диаграмма покупок мебели",coos,cois,orders);
                demo.pack();
                RefineryUtilities.centerFrameOnScreen(demo);
                demo.setVisible(true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
    private class WriteFile implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("FindOrderByUserId");
            try {
                coos.writeObject("FindOrderByUserId");
                coos.writeObject(user.getIdUser());
                List<Order>orders=new ArrayList<Order>();
                orders=(List<Order>)cois.readObject();
                TextFileWriter.writeCustomerOrdersToFile(orders);
                JOptionPane.showMessageDialog(null,"Заказы записаны в файл успешно!");
        } catch (IOException ex) {
        throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class activeDeliveriesAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("чунга чанга");
            ActiveDeliveries activeDeliveries = new ActiveDeliveries(coos, cois,user);
            System.out.println("чунга чанга");
            setVisible(false);
        }
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
            LogIn logIn=new LogIn(coos,cois);
            setVisible(false);
        }
    }

}
