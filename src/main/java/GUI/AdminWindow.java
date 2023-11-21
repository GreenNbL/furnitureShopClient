package GUI;

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
    private JButton openButton2;
    private JPanel AdminPanel;
    private JComboBox entityBox;;
    String[] items = {
            "1. Пользователи",
            "2. Мебель",
            "3. Заказы",
            "4. Поставщики",
            "5. Доставки"
    };
    ObjectOutputStream coos;
    ObjectInputStream cois;
    public AdminWindow(ObjectOutputStream coos, ObjectInputStream cois)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(AdminPanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        exitButton.addActionListener(new ExitAction());
        this.addWindowListener(new WindowClosing());
        addItemsEntituComboBox();
        openButton.addActionListener(new OpenAction());
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
    private void addItemsEntituComboBox()
    {
        for(int i=0;i<items.length;i++)
        {
            entityBox.addItem(items[i]);
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
