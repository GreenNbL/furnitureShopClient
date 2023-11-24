package GUI.Statistics.BarCharts;

import java.awt.Color;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;

import org.jfree.chart.renderer.category.CategoryItemRenderer;

import org.jfree.data.category.CategoryDataset;

import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;

public class BarChart extends JFrame
{
    private static final long serialVersionUID = 1L;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private List<Date> startDate;
    private List<Date> endDate;
    private String year;

    public BarChart(final String title, ObjectOutputStream coos, ObjectInputStream cois, List<Date> startDate,
    List<Date> endDate,String year)
    {
        super(title);
        this.cois=cois;
        this.coos=coos;
        this.startDate=startDate;
        this.endDate=endDate;
        this.year=year;
        CategoryDataset dataset    = Dataset.createDataset1(coos,cois,startDate,endDate);
        JFreeChart      chart      = createChart(dataset);
        ChartPanel      chartPanel = new ChartPanel(chart);
       // chartPanel.setPreferredSize(new Dimension(480, 320));
        setContentPane(chartPanel);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private JFreeChart createChart(final CategoryDataset dataset)
    {
        final JFreeChart chart = ChartFactory.createBarChart(
                "Продажи за "+year,   // chart title
                "Месяц",                  // domain axis label
                "Руб.",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        // Определение фона диаграммы
        chart.setBackgroundPaint(Color.white);

        // Настройка plot'а
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint    (new Color(212, 212, 248));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint (Color.white);

        // Настройка значений меток NumberAxis
        // (только целочисленные значения)
        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        axis.setUpperMargin(0.15);
        CategoryAxis axis_d = plot.getDomainAxis();
        axis_d.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        return chart;
    }

}
