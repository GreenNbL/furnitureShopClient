package GUI.Statistics.BarCharts;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;
import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.List;

public class CircleChart extends JFrame {
    private static final long serialVersionUID = 1L;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private List<Date> startDate;
    private List<Date> endDate;
    private String year;
    PieDataset dataset ;
    JFreeChart chart;
    PieSectionLabelGenerator pslg = null;
    public CircleChart(final String title, ObjectOutputStream coos, ObjectInputStream cois)
    {
        super(title);
        this.cois=cois;
        this.coos=coos;
        dataset = Dataset.createPieDatasetForFurniture(coos,cois);
        chart = createChart(dataset);
        // Размещение диаграммы в панели
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        setContentPane(chartPanel);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    public CircleChart(final String title, ObjectOutputStream coos, ObjectInputStream cois, List<Date> startDate,
                       List<Date> endDate,String year)
    {
        super(title);
        this.cois=cois;
        this.coos=coos;
        this.startDate=startDate;
        this.endDate=endDate;
        this.year=year;
        dataset = Dataset.createPieDataset(coos,cois,startDate,endDate);
        chart = createChart(dataset);

        // Размещение диаграммы в панели
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        setContentPane(chartPanel);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    private JFreeChart createChart(final PieDataset dataset)
    {
        chart = ChartFactory.createPieChart("Диаграмма объема продаж за "+year+" год",
                dataset, true, false, true);
        PiePlot plot = (PiePlot) chart.getPlot();
        pslg = new StandardPieSectionLabelGenerator("{0} = {1}шт.",
                NumberFormat.getNumberInstance(),
                NumberFormat.getPercentInstance());
        plot.setLabelGenerator(pslg);
        plot.setLabelGap(0.02);
        return chart;
    }
}

