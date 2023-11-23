package GUI.Statistics;

import GUI.AdminGUI.AdminWindow;
import GUI.AdminGUI.UsersTable;
import models.Order;
import models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

public class ProfitMonthWindow extends JFrame{
    private JPanel UsersTablePanel;
    private JPanel root;
    private JScrollPane scroll;
    private JTable tableOrders;
    private JButton backButton;
    private JTextField profitField;
    private DefaultTableModel tableModel;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private Date startDate;
    private Date endDate;
    public ProfitMonthWindow(ObjectOutputStream coos, ObjectInputStream cois,Date startDate,Date endDate)
    {
        this.coos=coos;
        this.cois=cois;
        this.startDate=startDate;
        this.endDate=endDate;
        setVisible(true);
        setContentPane(UsersTablePanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        backButton.addActionListener(new BackAction());
        ShowData();
    }
    public ProfitMonthWindow(ObjectOutputStream coos, ObjectInputStream cois,Date startDate,Date endDate,String stm)
    {
        this.coos=coos;
        this.cois=cois;
        this.startDate=startDate;
        this.endDate=endDate;
        setVisible(true);
        setContentPane(UsersTablePanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        backButton.addActionListener(new BackAction2());
        ShowData();
    }
    private class BackAction2 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            ManuallyMonthWindow manuallyMonthWindow = new ManuallyMonthWindow(coos, cois);
            setVisible(false);
        }
    }

    private class BackAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            MonthWindow monthWindow=new MonthWindow(coos,cois);
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
        Object[] columnTitle = {"Мебель", "Производитель", "Цена", "Количество","Общая стоимость","Дата заказа"};
        tableModel = new DefaultTableModel(null, columnTitle);
        tableOrders.setModel(tableModel);
        tableModel.getDataVector().removeAllElements();
        double sum=0;
        try {
            coos.writeObject("GetOrderByPeriod");
            coos.writeObject(startDate);
            coos.writeObject(endDate);
            System.out.println(startDate);
            System.out.println(endDate);
            List<Order> orders=new ArrayList<Order>();
            orders=(List<Order>)cois.readObject();
            if(orders!=null) {
                for (Order order : orders) {
                    Object[] data = {
                            order.getFurniture().getNameFurniture(),
                            order.getFurniture().getProvider().getCountry() + " " + order.getFurniture().getProvider().getCompany(),
                            order.getFurniture().getPrice(),
                            order.getAmount(),
                            order.getTotalCost(),
                            order.getDateOrder()
                    };
                    sum += order.getTotalCost();
                    tableModel.addRow(data);
                }
            }
            profitField.setText("Общая прибыль:"+sum+"руб.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
