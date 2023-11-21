package GUI;

import javax.swing.*;

import models.Provider;
import models.User;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddEditProvider extends JFrame {
    private JPanel SignUpPanel;
    private JTextField countryField;
    private JTextField companyField;
    private JTextField emailField;
    private JButton addButton;
    private JButton backButton;
    private JPanel AddEditPanel;
    private JLabel nameOfActionLabel;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private String role;
    private JTable tableProviders;
    private Provider provider;
    public AddEditProvider(ObjectOutputStream coos, ObjectInputStream cois,String nameOfAction)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(AddEditPanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        nameOfActionLabel.setText(nameOfAction);
        addButton.setText(nameOfAction);
        addButton.addActionListener(new AddAction());
        backButton.addActionListener(new backAction());
    }
    public AddEditProvider(ObjectOutputStream coos, ObjectInputStream cois, JTable tableProviders, Provider provider)
    {
        this.coos=coos;
        this.cois=cois;
        this.tableProviders=tableProviders;
        this.provider=provider;
        setVisible(true);
        setContentPane(AddEditPanel);
        setLocationRelativeTo(null);
        setSize(500,300);
        this.addWindowListener(new WindowClosing());
        nameOfActionLabel.setText("Редактирование поставщика");
        addButton.setText("Редактировать");
        addButton.addActionListener(new EditAction());
        backButton.addActionListener(new backAction());
        countryField.setText(provider.getCountry());
        companyField.setText(provider.getCompany());
        emailField.setText(provider.getEmail());
    }
    private class EditAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            TableModel model =  tableProviders.getModel();
            provider.setCountry(countryField.getText());
            provider.setCompany(companyField.getText());
            provider.setEmail(emailField.getText());
            try {
                coos.writeObject("EditProvider");
                coos.writeObject(provider);
                JOptionPane.showMessageDialog(null, "Изменения применены успешно.");
                setVisible(false);
                ProviderTable providerTable = new ProviderTable(coos, cois);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            Provider provider=new Provider();
            provider.setCountry(countryField.getText());
            provider.setCompany(companyField.getText());
            provider.setEmail(emailField.getText());
            try {
                coos.writeObject("AddProvider");
                coos.writeObject(provider);
                JOptionPane.showMessageDialog(null, "Поставщик успешно добавлен.");
                setVisible(false);
                ProviderTable providerTable = new ProviderTable(coos, cois);
            } catch (IOException ex) {
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
