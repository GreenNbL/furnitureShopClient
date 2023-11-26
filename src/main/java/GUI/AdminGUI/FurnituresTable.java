package GUI.AdminGUI;

import models.Furniture;
import models.Provider;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FurnituresTable extends JFrame{
    private JPanel FurnitureTablePanel;
    private JPanel root;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JTable tableFurnitures;
    private JButton AddButton;
    private JButton DeleteButton;
    private JButton EditButton;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;

    public FurnituresTable(ObjectOutputStream coos, ObjectInputStream cois)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(FurnitureTablePanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        AddButton.addActionListener(new AddAction());
        EditButton.addActionListener(new EditAction());
        DeleteButton.addActionListener(new DeleteAction());
        ShowData();
    }
    private class DeleteAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            TableModel model = tableFurnitures.getModel();
            Furniture furniture = new Furniture();
            //user.setIdUser(Integer.valueOf(model.getValueAt(tableUsers.getSelectedRow(),0).toString()));
            furniture.setIdFurniture(Integer.valueOf(model.getValueAt(tableFurnitures.getSelectedRow(),0).toString()));

            if(model.getValueAt(tableFurnitures.getSelectedRow(),1)!=null) {
                Provider provider =new Provider();
                try {
                    coos.writeObject("FindProviderById");
                    coos.writeObject(model.getValueAt(tableFurnitures.getSelectedRow(),1));
                    provider=(Provider)cois.readObject();
                    furniture.setProvider(provider);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
            //    furniture.setIdProvider(furniture.getIdProvider());
            //furniture.setIdProvider(Integer.valueOf(model.getValueAt(tableFurnitures.getSelectedRow(),1).toString()));
            if(model.getValueAt(tableFurnitures.getSelectedRow(),2)!=null)
                furniture.setNameFurniture(model.getValueAt(tableFurnitures.getSelectedRow(),2).toString());

            if(model.getValueAt(tableFurnitures.getSelectedRow(),3)!=null)
                furniture.setPrice(Double.valueOf(model.getValueAt(tableFurnitures.getSelectedRow(),3).toString()));

            if(model.getValueAt(tableFurnitures.getSelectedRow(),4)!=null)
                furniture.setAmounStock(Integer.valueOf(model.getValueAt(tableFurnitures.getSelectedRow(),4).toString()));
            try {
                coos.writeObject("DeleteFurniture");
                coos.writeObject(furniture);
                setVisible(false);
                FurnituresTable furnituresTable= new FurnituresTable(coos, cois);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class EditAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            TableModel model = tableFurnitures.getModel();
            Furniture furniture = new Furniture();
            furniture.setIdFurniture(Integer.valueOf(model.getValueAt(tableFurnitures.getSelectedRow(),0).toString()));

            Provider provider =new Provider();
            try {
                coos.writeObject("FindProviderById");
                coos.writeObject(model.getValueAt(tableFurnitures.getSelectedRow(),1));
                provider=(Provider)cois.readObject();
                furniture.setProvider(provider);
                furniture.setIdProvider(provider.getIdProvider());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            // if(model.getValueAt(tableFurnitures.getSelectedRow(),1)!=null)
                //furniture.setIdProvider(Integer.valueOf(model.getValueAt(tableFurnitures.getSelectedRow(),1).toString()));

            if(model.getValueAt(tableFurnitures.getSelectedRow(),2)!=null)
                furniture.setNameFurniture(model.getValueAt(tableFurnitures.getSelectedRow(),2).toString());

            if(model.getValueAt(tableFurnitures.getSelectedRow(),3)!=null)
                furniture.setPrice(Double.valueOf(model.getValueAt(tableFurnitures.getSelectedRow(),3).toString()));

            if(model.getValueAt(tableFurnitures.getSelectedRow(),4)!=null)
                furniture.setAmounStock(Integer.valueOf(model.getValueAt(tableFurnitures.getSelectedRow(),4).toString()));

            AddEditFurniture addEditFurniture =new AddEditFurniture(coos,cois,tableFurnitures,furniture);
            setVisible(false);
        }
    }
    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            //AddEditUserTable addEditUserTable =new AddEditUserTable(coos,cois,"Добавление пользователя");
            AddEditFurniture addEditFurniture =new AddEditFurniture(coos,cois,"Добавление мебели");
            setVisible(false);
        }
    }
    private class WindowClosing extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e) {
            //Client.closeApp();
            //System.exit(0);
            setVisible(false);
        }
    }
    public void ShowData() {
        Object[] columnTitle = {"id_furniture","id_provider", "мебель", "цена", "штук в наличии"};
        //tableFurnitures = new DefaultTableModel(null, columnTitle);
        tableModel = new DefaultTableModel(null, columnTitle);
        tableFurnitures.setModel(tableModel);
        tableModel.getDataVector().removeAllElements();
        System.out.println("Connected in Action_dialog");
        try {
            coos.writeObject("GetAllFurnitures");
            List<Furniture> furnitures=new ArrayList<Furniture>();
            furnitures=(List<Furniture>)cois.readObject();
            System.out.println(furnitures);
            String provider;
            for(Furniture furniture:furnitures)
            {
                if( furniture.getProvider()==null)
                    provider=null;
                else
                    provider=String.valueOf(furniture.getIdProvider());

                Object[] data = {
                        furniture.getIdFurniture(),
                        provider,
                        furniture.getNameFurniture(),
                        furniture.getPrice(),
                        furniture.getAmounStock()
                };
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}


