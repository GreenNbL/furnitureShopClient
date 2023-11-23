package GUI.AdminGUI;

import GUI.*;
import GUI.Statistics.ManuallyMonthWindow;
import GUI.Statistics.MonthWindow;
import client.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AdminWindow extends JFrame {
    private JComboBox statisticComboBox;
    private JButton exitButton;
    private JButton openButton;
    private JButton applyButton;
    private JPanel AdminPanel;
    private JComboBox entityBox;;
    String[] items = {
            "1. Пользователи",
            "2. Мебель",
            "3. Заказы",
            "4. Поставщики",
            "5. Доставки"
    };
    Object[] itemsStatistics = {
            "1. Рассчитать прибыль за месяц",
            "2. Рассчитать прибыль за промежуток времени",
            "3. Заказы",
            "4. Поставщики",
            "5. Доставки"
    };
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    public AdminWindow(ObjectOutputStream coos, ObjectInputStream cois)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(AdminPanel);
        setLocationRelativeTo(null);
        setSize(700,300);
        exitButton.addActionListener(new ExitAction());
        this.addWindowListener(new WindowClosing());
        addItemsEntityComboBox();
        addItemsStatisticsComboBox();
        openButton.addActionListener(new OpenAction());
        applyButton.addActionListener(new  ApplyAction());
    }
    private class ApplyAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            int number=statisticComboBox.getSelectedIndex();
            System.out.println(statisticComboBox.getSelectedItem());
            switch(number)
            {
                case 0: {
                   MonthWindow monthWindow=new MonthWindow(coos, cois);
                    break;
                }
                case 1: {
                   ManuallyMonthWindow manuallyMonthWindow = new ManuallyMonthWindow(coos, cois);
                    break;
                }
                case 2: {
                    OrderTable orderTable = new OrderTable(coos, cois);
                    break;
                }
                case 3: {
                    ProviderTable providerTable = new ProviderTable(coos, cois);
                    break;
                }
                case 4: {
                    DeliveryTable deliveryTable =new DeliveryTable(coos, cois);
                    break;
                }
            }

        }
    }
    private class OpenAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            int number=entityBox.getSelectedIndex();
            System.out.println(number);
            switch(number)
            {
                case 0: {
                    UsersTable usersTable = new UsersTable(coos, cois);
                    break;
                }
                case 1: {
                    FurnituresTable furnituresTable = new FurnituresTable(coos, cois);
                    break;
                }
                case 2: {
                    OrderTable orderTable = new OrderTable(coos, cois);
                    break;
                }
                case 3: {
                    ProviderTable providerTable = new ProviderTable(coos, cois);
                    break;
                }
                case 4: {
                    DeliveryTable deliveryTable =new DeliveryTable(coos, cois);
                    break;
                }
            }

        }
    }
    private void addItemsEntityComboBox()
    {
        for(int i=0;i<items.length;i++)
        {
            entityBox.addItem(items[i]);
        }
    }
    private void addItemsStatisticsComboBox()
    {
        for(int i=0;i<itemsStatistics.length;i++)
        {
            statisticComboBox.addItem(itemsStatistics[i]);
        }
    }

    private class ExitAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            LogIn logIn=new LogIn(coos,cois);
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
