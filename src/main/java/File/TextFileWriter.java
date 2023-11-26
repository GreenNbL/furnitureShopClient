package File;
import models.Order;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TextFileWriter {
    public static void writeAllOrdersToFile(List<Order>orders) {
            try (FileWriter writer = new FileWriter("Orders.txt", false)) {
                for(Order order:orders) {
                    if(order.getFurniture().getProvider()!=null)
                        writer.write(order.getFurniture().getNameFurniture() + " " + order.getFurniture().getProvider().getCountry() + " " +
                                order.getFurniture().getProvider().getCompany() + " " +
                                order.getAmount() + "шт. " +
                                order.getTotalCost() + "руб.\n");
                    else
                        writer.write(order.getFurniture().getNameFurniture() + " "+
                                order.getAmount() + "шт. " +
                                order.getTotalCost() + "руб.\n");
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }
    public static void writeCustomerOrdersToFile(List<Order>orders) {
        try (FileWriter writer = new FileWriter("YourOrders.txt", false)) {
            for(Order order:orders) {
                if(order.getFurniture().getProvider()!=null)
                    writer.write(order.getFurniture().getNameFurniture() + " " + order.getFurniture().getProvider().getCountry() + " " +
                            order.getFurniture().getProvider().getCompany() + " " +
                            order.getAmount() + "шт. " +
                            order.getTotalCost() + "руб.\n");
                else
                    writer.write(order.getFurniture().getNameFurniture() + " "+
                            order.getAmount() + "шт. " +
                            order.getTotalCost() + "руб.\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}