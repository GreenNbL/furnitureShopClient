package GUI.Statistics;

import GUI.AdminGUI.AdminWindow;
import GUI.Statistics.BarCharts.BarChart;
import GUI.Statistics.BarCharts.CircleChart;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class YearsWindow extends JFrame{
    private JButton backButton;
    private JButton applyButton;
    private JComboBox yearComboBox;
    private JPanel yearsPanel;

    private ObjectOutputStream coos;
    private ObjectInputStream cois;
   // private Object[] months={"1. Январь","2. Февраль","3. Март","4. Апрель","5. Май","6. Июнь","7. Июль","8. Август","9. Сентябрь","10. Октябрь","11. Ноябрь","12. Декабрь"};
    private Object[] years={"2015","2016","2017","2018","2019","2020","2021","2022","2023"};
    public YearsWindow(ObjectOutputStream coos, ObjectInputStream cois)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(yearsPanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        addYearComboBox();
        backButton.addActionListener(new BackAction());
        applyButton.addActionListener(new ApplyAction());
    }
    public YearsWindow(ObjectOutputStream coos, ObjectInputStream cois,String circleChart)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(yearsPanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        addYearComboBox();
        backButton.addActionListener(new BackAction());
        applyButton.addActionListener(new ApplyAction2());
    }
    private void addYearComboBox()
    {
        for(int i=0;i<years.length;i++)
        {
            yearComboBox.addItem(years[i]);
        }
    }

    private class ApplyAction2 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            // int numberOfMonth=monthsComboBox.getSelectedIndex()+1;
            List<Date> startDate= new ArrayList<>();
            List<Date> endDate= new ArrayList<>();
            for(int i=1;i<=12;i++) {
                switch (i) {
                    case 1:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-01-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-01-31"));
                        break;
                    case 2:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-02-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-02-28"));
                        break;
                    case 3:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-03-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-03-31"));
                        break;
                    case 4:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-04-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-04-30"));
                        break;
                    case 5:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-05-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-05-31"));
                        break;
                    case 6:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-06-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-06-30"));
                        break;
                    case 7:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-07-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-07-31"));
                        break;
                    case 8:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-08-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-08-31"));
                        break;
                    case 9:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-09-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-09-30"));
                        break;
                    case 10:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-10-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-10-31"));
                        break;
                    case 11:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-11-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-11-30"));
                        break;
                    case 12:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-12-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-12-31"));
                        break;
                }
            }
            setVisible(false);
            CircleChart demo = new CircleChart("Круговая диаграмма",coos,cois,startDate,endDate,String.valueOf(yearComboBox.getSelectedItem()));
            demo.pack();
            RefineryUtilities.centerFrameOnScreen(demo);
            demo.setVisible(true);
        }
    }
    private class ApplyAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
           // int numberOfMonth=monthsComboBox.getSelectedIndex()+1;
            List<Date> startDate= new ArrayList<>();
            List<Date> endDate= new ArrayList<>();
            for(int i=1;i<=12;i++) {
                switch (i) {
                    case 1:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-01-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-01-31"));
                        break;
                    case 2:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-02-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-02-28"));
                        break;
                    case 3:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-03-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-03-31"));
                        break;
                    case 4:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-04-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-04-30"));
                        break;
                    case 5:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-05-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-05-31"));
                        break;
                    case 6:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-06-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-06-30"));
                        break;
                    case 7:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-07-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-07-31"));
                        break;
                    case 8:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-08-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-08-31"));
                        break;
                    case 9:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-09-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-09-30"));
                        break;
                    case 10:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-10-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-10-31"));
                        break;
                    case 11:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-11-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-11-30"));
                        break;
                    case 12:
                        startDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-12-01"));
                        endDate.add(Date.valueOf(yearComboBox.getSelectedItem() + "-12-31"));
                        break;
                }
            }
            setVisible(false);
            final BarChart demo = new BarChart("Столбчатая диаграмма",coos,cois,startDate,endDate,String.valueOf(yearComboBox.getSelectedItem()));
            demo.pack();
            RefineryUtilities.centerFrameOnScreen(demo);
            demo.setVisible(true);

        }
    }
    private class BackAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
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
