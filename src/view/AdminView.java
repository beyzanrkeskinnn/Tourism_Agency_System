package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private DefaultTableModel tbl_users_model = new DefaultTableModel();
    private JPanel container;
    private JLabel lbl_user_welcome;
    private JTabbedPane pnl_users;
    private JTable tbl_users;
    private JComboBox cmb_user_role;
    private JButton btn_user_search;
    private JButton btn_user_clear;
    private JButton btn_logout;
    private UserManager usermanager;
    private User user;
    private JPopupMenu user_menu;
    private Object[] col_user;


    public AdminView(User user) { //user registration page
        this.usermanager = new UserManager();
        this.add(container);
        this.guiInitilaze(1000, 700);
        this.user = user;
        if (user == null) {
            dispose();
        }
        this.lbl_user_welcome.setText("Hoşgeldiniz " + this.user.getUsername());
        loadUserTable(null); //user table
        loadUserComponent(); //user settings
        loadUserFilter(); //filter users
        loadComponent(); //return to user login page

    }
    private void loadComponent() {
        this.btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView loginView = new LoginView();
            }
        });
    }
    public void loadUserTable(ArrayList<Object[]> userList) {
        this.col_user = new Object[]{"Id", "Adı", "Soyadı", "Kullanıcı Adı", "Şifresi", "Rolü"};
        if (userList == null) {
            userList = this.usermanager.getForTable(this.col_user.length, this.usermanager.findAll());
        }
        this.createTable(this.tbl_users_model, this.tbl_users, col_user, userList);

    }
    public void loadUserComponent() {
        tableRowSelect(tbl_users);
        tbl_users.setEnabled(false);
        this.user_menu = new JPopupMenu();
        user_menu.add("Yeni").addActionListener(e -> {
            UserView userView = new UserView(new User());
            userView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                    loadUserTable(null);
                }
            });
        });
        user_menu.add("Güncelle").addActionListener(e -> {
            tableRowSelect(tbl_users);
            int selectUserId = this.getTableSelectedRow(tbl_users, 0);
            UserView userView = new UserView(this.usermanager.getById(selectUserId));
            userView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable(null);
                }
            });
        });
        user_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectModelId = this.getTableSelectedRow(tbl_users, 0);
                if (this.usermanager.delete(selectModelId)) {
                    Helper.showMsg("done");
                    loadUserTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_users.setComponentPopupMenu(user_menu);

        btn_user_search.addActionListener(e -> {
            ArrayList<User> carList = this.usermanager.searchForTable(

                    (User.Role) cmb_user_role.getSelectedItem()
            );

            ArrayList<Object[]> userRow = this.usermanager.getForTable(this.col_user.length, carList);
            loadUserTable(userRow);
        });
        btn_user_clear.addActionListener(e -> {
            loadUserTable(null);
        });


    }
    public void loadUserFilter() {
        this.cmb_user_role.setModel(new DefaultComboBoxModel<>(User.Role.values()));
        this.cmb_user_role.setSelectedItem(null);

    }

}
