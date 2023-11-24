package GUI.Statistics.BarCharts;

import models.Furniture;
import models.Order;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Dataset
{
    private static String[] months={"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    public static CategoryDataset createDataset1(ObjectOutputStream coos, ObjectInputStream cois,List<Date> startDate, List<Date> endDate)
    {
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
        // row keys...
        final String series1 = "Объем продаж, руб.";
        for(int i=0;i<12;i++)
        {
            dataset.addValue(findByDate(coos,cois,startDate.get(i),endDate.get(i)), series1, months[i]);
        }
        return dataset;
    }
    public static List<Furniture> findAllFurniture(ObjectOutputStream coos, ObjectInputStream cois) {
        try {
            coos.writeObject("GetAllFurnitures");
            List<Furniture> furnitures=new ArrayList<Furniture>();
            furnitures=(List<Furniture>)cois.readObject();
            return furnitures;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static Double findByDate(ObjectOutputStream coos, ObjectInputStream cois,Date startDate, Date endDate) {
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
                    sum += order.getTotalCost();
                }
            }
            return sum;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static int findByDateAndIdFurniture(ObjectOutputStream coos, ObjectInputStream cois,Date startDate, Date endDate,int id) {
        int amount=0;
        try {
            coos.writeObject("GetOrderByPeriodAndIdFurniture");
            coos.writeObject(startDate);
            coos.writeObject(endDate);
            coos.writeObject(id);
            System.out.println(id);
            List<Order> orders=new ArrayList<Order>();
            orders=(List<Order>)cois.readObject();
            System.out.println(orders);
            if(orders!=null) {
                for (Order order : orders) {
                    amount += order.getAmount();
                }
            }
            return amount;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static int findAllOrders(ObjectOutputStream coos, ObjectInputStream cois,int idFurniture) {
        int amount=0;
        try {
            coos.writeObject("FindOrderByFurnitureId");
            coos.writeObject(idFurniture);
            List<Order> orders=new ArrayList<Order>();
            orders=(List<Order>)cois.readObject();
            if(orders!=null) {
                for (Order order : orders) {
                    amount += order.getAmount();
                }
            }
            return amount;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static PieDataset createPieDataset(ObjectOutputStream coos, ObjectInputStream cois,List<Date> startDate, List<Date> endDate)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int i = 0; i < months.length; i++)
            dataset.setValue(months[i], findByDate(coos,cois,startDate.get(i),endDate.get(i)));
        return dataset;
    }
    public static PieDataset createPieDatasetByDateAndIdFurniture(ObjectOutputStream coos, ObjectInputStream cois,Date startDate, Date endDate)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Furniture> furnitures=findAllFurniture(coos,cois);
        for (int i = 0; i < furnitures.size(); i++)
            dataset.setValue(furnitures.get(i).getNameFurniture()+" "
                            +furnitures.get(i).getProvider().getCountry()+" "
                            +furnitures.get(i).getProvider().getCompany()
                    , findByDateAndIdFurniture(coos,cois,startDate,endDate,furnitures.get(i).getIdFurniture()));
        return dataset;
    }
    public static PieDataset createPieDatasetForFurniture(ObjectOutputStream coos, ObjectInputStream cois)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Furniture> furnitures=findAllFurniture(coos,cois);
        for (int i = 0; i < furnitures.size(); i++)
            dataset.setValue(furnitures.get(i).getNameFurniture()+" "
                            +furnitures.get(i).getProvider().getCountry()+" "
                            +furnitures.get(i).getProvider().getCompany()
                    , findAllOrders(coos,cois,furnitures.get(i).getIdFurniture()));
        return dataset;
    }
}
