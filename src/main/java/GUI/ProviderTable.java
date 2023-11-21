package GUI;
import client.Client;
import models.Provider;
import models.User;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderTable extends JFrame{
    private JPanel root;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JTable tableProviders;
    private JButton AddButton;
    private JButton DeleteButton;
    private JButton EditButton;
    private JPanel ProviderTablePanel;
    ObjectOutputStream coos;
    ObjectInputStream cois;
    public ProviderTable(ObjectOutputStream coos, ObjectInputStream cois)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(ProviderTablePanel);
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
            TableModel model = tableProviders.getModel();
            Provider provider =new Provider();
            provider.setIdProvider(Integer.valueOf(model.getValueAt(tableProviders.getSelectedRow(),0).toString()));

            if(model.getValueAt(tableProviders.getSelectedRow(),1)!=null)
                provider.setCountry(model.getValueAt(tableProviders.getSelectedRow(),1).toString());

            if(model.getValueAt(tableProviders.getSelectedRow(),2)!=null)
                provider.setCompany(model.getValueAt(tableProviders.getSelectedRow(),2).toString());

            if(model.getValueAt(tableProviders.getSelectedRow(),3)!=null)
                provider.setEmail(model.getValueAt(tableProviders.getSelectedRow(),3).toString());
            try {
                coos.writeObject("DeleteProvider");
                coos.writeObject(provider);
                JOptionPane.showMessageDialog(null, "Поставщик успешно удален.");
                setVisible(false);
                ProviderTable providerTable = new ProviderTable(coos, cois);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class EditAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            TableModel model = tableProviders.getModel();
            Provider provider =new Provider();
            provider.setIdProvider(Integer.valueOf(model.getValueAt(tableProviders.getSelectedRow(),0).toString()));

            if(model.getValueAt(tableProviders.getSelectedRow(),1)!=null)
                provider.setCountry(model.getValueAt(tableProviders.getSelectedRow(),1).toString());

            if(model.getValueAt(tableProviders.getSelectedRow(),2)!=null)
                provider.setCompany(model.getValueAt(tableProviders.getSelectedRow(),2).toString());

            if(model.getValueAt(tableProviders.getSelectedRow(),3)!=null)
                provider.setEmail(model.getValueAt(tableProviders.getSelectedRow(),3).toString());
            AddEditProvider addEditProvider=new AddEditProvider(coos,cois,tableProviders,provider);
            setVisible(false);
            //AddEditUserTable addEditUserTable =new AddEditUserTable(coos,cois,tableUsers,user);
        }
    }
    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddEditProvider addEditProvider =new AddEditProvider(coos,cois,"Добавление поставщика");
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
        Object[] columnTitle = {"id_provider","Страна", "Компания", "Email"};
        tableModel = new DefaultTableModel(null, columnTitle);
        tableProviders.setModel(tableModel);
        tableModel.getDataVector().removeAllElements();
        System.out.println("Connected in Action_dialog_provider");
        try {
            coos.writeObject("GetAllProviders");
            List<Provider>providers=new ArrayList<Provider>();
            providers=(List<Provider>)cois.readObject();
            System.out.println(providers);
            for(Provider provider:providers)
            {
                Object[] data = {
                        provider.getIdProvider(),
                        provider.getCountry(),
                        provider.getCompany(),
                        provider.getEmail()
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

