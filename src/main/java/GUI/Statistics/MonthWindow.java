package GUI.Statistics;

import GUI.AdminGUI.AdminWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;

public class MonthWindow extends JFrame{
    private JPanel monthPanel;
    private JComboBox monthsComboBox;
    private JButton backButton;
    private JButton applyButton;
    private JComboBox yearComboBox;
    private DefaultTableModel tableModel;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private Object[] months={"1. Январь","2. Февраль","3. Март","4. Апрель","5. Май","6. Июнь","7. Июль","8. Август","9. Сентябрь","10. Октябрь","11. Ноябрь","12. Декабрь"};
    private Object[] years={"2015","2016","2017","2018","2019","2020","2021","2022","2023"};
    public MonthWindow(ObjectOutputStream coos, ObjectInputStream cois)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(monthPanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        addMonthComboBox();
        addYearComboBox();
        backButton.addActionListener(new BackAction());
        applyButton.addActionListener(new ApplyAction());
    }
    private void addMonthComboBox()
    {
        for(int i=0;i<months.length;i++)
        {
            monthsComboBox.addItem(months[i]);
        }
    }
    private void addYearComboBox()
    {
        for(int i=0;i<years.length;i++)
        {
            yearComboBox.addItem(years[i]);
        }
    }

    private class ApplyAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            int numberOfMonth=monthsComboBox.getSelectedIndex()+1;
            Date startDate=null;
            Date endDate=null;
            switch(numberOfMonth) {
                case 1:
                    startDate=Date.valueOf(yearComboBox.getSelectedItem()+"-01-01");
                    endDate=Date.valueOf(yearComboBox.getSelectedItem()+"-01-31");
                    break;
                case 2:
                    startDate=Date.valueOf(yearComboBox.getSelectedItem()+"-02-01");
                    endDate=Date.valueOf(yearComboBox.getSelectedItem()+"-02-28");
                    break;
                case 3:
                    startDate=Date.valueOf(yearComboBox.getSelectedItem()+"-03-01");
                    endDate=Date.valueOf(yearComboBox.getSelectedItem()+"-03-31");
                    break;
                case 4:
                    startDate=Date.valueOf(yearComboBox.getSelectedItem()+"-04-01");
                    endDate=Date.valueOf(yearComboBox.getSelectedItem()+"-04-30");
                    break;
                case 5:
                    startDate=Date.valueOf(yearComboBox.getSelectedItem()+"-05-01");
                    endDate=Date.valueOf(yearComboBox.getSelectedItem()+"-05-31");
                    break;
                case 6:
                    startDate=Date.valueOf(yearComboBox.getSelectedItem()+"-06-01");
                    endDate=Date.valueOf(yearComboBox.getSelectedItem()+"-06-30");
                    break;
                case 7:
                    startDate=Date.valueOf(yearComboBox.getSelectedItem()+"-07-01");
                    endDate=Date.valueOf(yearComboBox.getSelectedItem()+"-07-31");
                    break;
                case 8:
                    startDate=Date.valueOf(yearComboBox.getSelectedItem()+"-08-01");
                    endDate=Date.valueOf(yearComboBox.getSelectedItem()+"-08-31");
                    break;
                case 9:
                    startDate=Date.valueOf(yearComboBox.getSelectedItem()+"-09-01");
                    endDate=Date.valueOf(yearComboBox.getSelectedItem()+"-09-30");
                    break;
                case 10:
                    startDate=Date.valueOf(yearComboBox.getSelectedItem()+"-10-01");
                    endDate=Date.valueOf(yearComboBox.getSelectedItem()+"-10-31");
                    break;
                case 11:
                    startDate=Date.valueOf(yearComboBox.getSelectedItem()+"-11-01");
                    endDate=Date.valueOf(yearComboBox.getSelectedItem()+"-11-30");
                    break;
                case 12:
                    startDate=Date.valueOf(yearComboBox.getSelectedItem()+"-12-01");
                    endDate=Date.valueOf(yearComboBox.getSelectedItem()+"-12-31");
                    break;
            }
            ProfitMonthWindow profitMonthWindow=new ProfitMonthWindow(coos,cois,startDate,endDate);
            setVisible(false);
        }
    }
    private class BackAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            AdminWindow adminWindow=new AdminWindow(coos,cois);
            setVisible(false);
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
