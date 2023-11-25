package GUI.Statistics;

import GUI.AdminGUI.AdminWindow;

import javax.swing.*;
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
    private JTextField startField;
    private JTextField endField;
    private JPanel manuallyDatePanel;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    public ManuallyMonthWindow(ObjectOutputStream coos, ObjectInputStream cois)
    {
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
            Date startDate=Date.valueOf(startField.getText());
            Date endDate=Date.valueOf(endField.getText());
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
