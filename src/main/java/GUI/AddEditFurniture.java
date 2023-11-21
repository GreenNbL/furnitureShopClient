package GUI;

import models.Furniture;
import models.Provider;

import javax.swing.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEditFurniture extends JFrame {
    private JPanel AddEditPanel;
    private JPanel SignUpPanel;
    private JTextField costlField;
    private JButton addButton;
    private JLabel nameOfActionLabel;
    private JButton backButton;
    private JTextField furnitureNameField;
    private JTextField amountInstockField;
    private JComboBox providerComboBox;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private JTable tableFurnitures;
    private Furniture furniture;

        public AddEditFurniture(ObjectOutputStream coos, ObjectInputStream cois,String nameOfAction)
        {
            this.coos=coos;
            this.cois=cois;
            setVisible(true);
            setContentPane(AddEditPanel);
            setLocationRelativeTo(null);
            setSize(800,300);
            this.addWindowListener(new WindowClosing());
            nameOfActionLabel.setText(nameOfAction);
            addButton.setText(nameOfAction);
            addButton.addActionListener(new AddAction());
            backButton.addActionListener(new backAction());
            try {
                coos.writeObject("GetAllProviders");
                List<Provider> providers=new ArrayList<Provider>();
                providers=(List<Provider>)cois.readObject();
                for (Provider provider: providers) {
                    providerComboBox.addItem(provider.toString());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        public AddEditFurniture(ObjectOutputStream coos, ObjectInputStream cois, JTable tableFurnitures, Furniture furniture)
        {
            this.coos=coos;
            this.cois=cois;
            this.tableFurnitures=tableFurnitures;
            this.furniture=furniture;
            setVisible(true);
            setContentPane(AddEditPanel);
            setLocationRelativeTo(null);
            setSize(800,300);
            this.addWindowListener(new WindowClosing());
            nameOfActionLabel.setText("Редактирование мебели");
            addButton.setText("Редактировать");
            addButton.addActionListener(new EditAction());
            backButton.addActionListener(new backAction());
            furnitureNameField.setText(furniture.getNameFurniture());
            costlField.setText(String.valueOf(furniture.getPrice()));
            amountInstockField.setText(String.valueOf(furniture.getAmounStock()));
            backButton.addActionListener(new backAction());
            try {
                coos.writeObject("GetAllProviders");
                List<Provider> providers=new ArrayList<Provider>();
                providers=(List<Provider>)cois.readObject();
                for (Provider provider: providers) {
                    providerComboBox.addItem(provider.toString());
                }
                providerComboBox.setSelectedIndex(furniture.getIdProvider()-1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        private int FindFirstNumberInString(String str) {
            Pattern pattern = Pattern.compile("\\d+"); // Регулярное выражение для поиска чисел
            Matcher matcher = pattern.matcher(str);

            if (matcher.find()) {
                String number = matcher.group();
                System.out.println( number);// Получение первого попавшегося числа
                return Integer.parseInt(number); // Преобразование найденной строки в число
                //System.out.println("Найденное число: " + firstNumber);
            }
            return 0;

        }
        private class EditAction implements ActionListener
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                Provider provider =new Provider();
                try {
                    coos.writeObject("FindProviderById");
                    coos.writeObject(FindFirstNumberInString(providerComboBox.getSelectedItem().toString()));
                    provider=(Provider)cois.readObject();
                    furniture.setProvider(provider);
                    //furniture.setIdProvider(FindFirstNumberInString(providerComboBox.getSelectedItem().toString()));
                    furniture.setNameFurniture( furnitureNameField.getText());
                    furniture.setPrice(Double.valueOf(costlField.getText()));
                    furniture.setAmounStock(Integer.valueOf(amountInstockField.getText()));
                    coos.writeObject("EditFurniture");
                    coos.writeObject(furniture);
                    JOptionPane.showMessageDialog(null, "Изменения применены успешно.");
                   // ProviderTable providerTable = new ProviderTable(coos, cois);
                    FurnituresTable furnituresTable= new FurnituresTable(coos, cois);
                    setVisible(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        private class AddAction implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                Furniture furniture =new Furniture();
                //furniture.setIdProvider(FindFirstNumberInString(providerComboBox.getSelectedItem().toString()));
                Provider provider =new Provider();
                try {
                    coos.writeObject("FindProviderById");
                    coos.writeObject(FindFirstNumberInString(providerComboBox.getSelectedItem().toString()));
                    provider=(Provider)cois.readObject();
                    furniture.setNameFurniture( furnitureNameField.getText());
                    furniture.setPrice(Integer.valueOf(costlField.getText()));
                    furniture.setAmounStock(Integer.valueOf(amountInstockField.getText()));
                    furniture.setProvider(provider);
                    coos.writeObject("AddFurniture");
                    coos.writeObject(furniture);
                    JOptionPane.showMessageDialog(null, "Мебель успешно добавлена.");
                    setVisible(false);
                    FurnituresTable furnituresTable= new FurnituresTable(coos, cois);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        private class backAction implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
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
