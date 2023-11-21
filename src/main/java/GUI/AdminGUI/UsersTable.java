package GUI.AdminGUI;

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
import java.util.ArrayList;
import java.util.List;

public class UsersTable extends JFrame{
    private JPanel root;
    private JScrollPane scroll;
    private DefaultTableModel tableModel;
    private JTable tableUsers;
    private JButton AddButton;
    private JButton DeleteButton;
    private JButton EditButton;
    private JPanel UsersTablePanel;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    public UsersTable(ObjectOutputStream coos, ObjectInputStream cois)
    {
        this.coos=coos;
        this.cois=cois;
        setVisible(true);
        setContentPane(UsersTablePanel);
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

            TableModel model = tableUsers.getModel();
            User user=new User();
            user.setIdUser(Integer.valueOf(model.getValueAt(tableUsers.getSelectedRow(),0).toString()));

            if(model.getValueAt(tableUsers.getSelectedRow(),1)!=null)
                user.setLogin(model.getValueAt(tableUsers.getSelectedRow(),1).toString());

            if(model.getValueAt(tableUsers.getSelectedRow(),2)!=null)
                user.setPassword(model.getValueAt(tableUsers.getSelectedRow(),2).toString());

            if(model.getValueAt(tableUsers.getSelectedRow(),3)!=null)
                user.setRole(model.getValueAt(tableUsers.getSelectedRow(),3).toString());

            if(model.getValueAt(tableUsers.getSelectedRow(),4)!=null)
                user.setSurname(model.getValueAt(tableUsers.getSelectedRow(),4).toString());

            if(model.getValueAt(tableUsers.getSelectedRow(),5)!=null)
                user.setName(model.getValueAt(tableUsers.getSelectedRow(),5).toString());

            if(model.getValueAt(tableUsers.getSelectedRow(),6)!=null)
                user.setTel_number(model.getValueAt(tableUsers.getSelectedRow(),6).toString());
            try {
                coos.writeObject("DeleteUser");
                coos.writeObject(user);
                setVisible(false);
                UsersTable usersTable = new UsersTable(coos, cois);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private class EditAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            TableModel model = tableUsers.getModel();
            User user=new User();
            user.setIdUser(Integer.valueOf(model.getValueAt(tableUsers.getSelectedRow(),0).toString()));

            if(model.getValueAt(tableUsers.getSelectedRow(),1)!=null)
                user.setLogin(model.getValueAt(tableUsers.getSelectedRow(),1).toString());

            if(model.getValueAt(tableUsers.getSelectedRow(),2)!=null)
                user.setPassword(model.getValueAt(tableUsers.getSelectedRow(),2).toString());

            if(model.getValueAt(tableUsers.getSelectedRow(),3)!=null)
                user.setRole(model.getValueAt(tableUsers.getSelectedRow(),3).toString());

            if(model.getValueAt(tableUsers.getSelectedRow(),4)!=null)
                user.setSurname(model.getValueAt(tableUsers.getSelectedRow(),4).toString());

            if(model.getValueAt(tableUsers.getSelectedRow(),5)!=null)
                user.setName(model.getValueAt(tableUsers.getSelectedRow(),5).toString());

            if(model.getValueAt(tableUsers.getSelectedRow(),6)!=null)
                user.setTel_number(model.getValueAt(tableUsers.getSelectedRow(),6).toString());
            AddEditUserTable addEditUserTable =new AddEditUserTable(coos,cois,tableUsers,user);
            setVisible(false);
        }
    }
    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddEditUserTable addEditUserTable =new AddEditUserTable(coos,cois,"Добавление пользователя");
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
        Object[] columnTitle = {"id_user","Логин", "Пароль", "Роль", "Фамилия","Имя","Телефонный номер"};
        tableModel = new DefaultTableModel(null, columnTitle);
        tableUsers.setModel(tableModel);
        tableModel.getDataVector().removeAllElements();
        System.out.println("Connected in Action_dialog");
        try {
            coos.writeObject("GetAllUsers");
            List<User>users=new ArrayList<User>();
            users=(List<User>)cois.readObject();
            System.out.println(users);
            for(User user:users)
            {
                Object[] data = {
                        user.getIdUser(),
                        user.getLogin(),
                        user.getPassword(),
                        user.getRole(),
                        user.getSurname(),
                        user.getName(),
                        user.getTel_number()
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
