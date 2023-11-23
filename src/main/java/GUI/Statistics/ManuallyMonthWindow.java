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

public class ManuallyMonthWindow extends JFrame{
    private JButton backButton;
    private JButton applyButton;
    private JTextField monthField;
    private JTextField yearField;
    private JPanel manuallyDatePanel;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    public ManuallyMonthWindow(ObjectOutputStream coos, ObjectInputStream cois)
    {
        System.out.println("хуй");
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(manuallyDatePanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        backButton.addActionListener(new BackAction());
        applyButton.addActionListener(new ApplyAction());
    }
    private class ApplyAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String month=monthField.getText().replaceAll("\\s", "");
            Date startDate=null;
            Date endDate=null;
            switch(month.toLowerCase()) {
                case "январь":
                    startDate=Date.valueOf(yearField.getText()+"-01-01");
                    endDate=Date.valueOf(yearField.getText()+"-01-31");
                    break;
                case "февраль":
                    startDate=Date.valueOf(yearField.getText()+"-02-01");
                    endDate=Date.valueOf(yearField.getText()+"-02-28");
                    break;
                case "март":
                    startDate=Date.valueOf(yearField.getText()+"-03-01");
                    endDate=Date.valueOf(yearField.getText()+"-03-31");
                    break;
                case "апрель":
                    startDate=Date.valueOf(yearField.getText()+"-04-01");
                    endDate=Date.valueOf(yearField.getText()+"-04-30");
                    break;
                case "май":
                    startDate=Date.valueOf(yearField.getText()+"-05-01");
                    endDate=Date.valueOf(yearField.getText()+"-05-31");
                    break;
                case "июнь":
                    startDate=Date.valueOf(yearField.getText()+"-06-01");
                    endDate=Date.valueOf(yearField.getText()+"-06-30");
                    break;
                case "июль":
                    startDate=Date.valueOf(yearField.getText()+"-07-01");
                    endDate=Date.valueOf(yearField.getText()+"-07-31");
                    break;
                case "август":
                    startDate=Date.valueOf(yearField.getText()+"-08-01");
                    endDate=Date.valueOf(yearField.getText()+"-08-31");
                    break;
                case "сентябрь":
                    startDate=Date.valueOf(yearField.getText()+"-09-01");
                    endDate=Date.valueOf(yearField.getText()+"-09-30");
                    break;
                case "октябрь":
                    startDate=Date.valueOf(yearField.getText()+"-10-01");
                    endDate=Date.valueOf(yearField.getText()+"-10-31");
                    break;
                case "ноябрь":
                    startDate=Date.valueOf(yearField.getText()+"-11-01");
                    endDate=Date.valueOf(yearField.getText()+"-11-30");
                    break;
                case "декабрь":
                    startDate=Date.valueOf(yearField.getText()+"-12-01");
                    endDate=Date.valueOf(yearField.getText()+"-12-31");
                    break;
            }
            ProfitMonthWindow profitMonthWindow=new ProfitMonthWindow(coos,cois,startDate,endDate,"manually");
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
