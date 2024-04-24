package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginView extends Layout {
    private JPanel container;
    private JTextField fld_user_name;
    private JTextField fld_user_password;
    private JButton btn_login;
    private JTable tbl_user;
    private UserManager usermanager;
    private User user;
    private Object[] col_user;
    private DefaultTableModel tbl_users_model = new DefaultTableModel();

    //user interface
    public LoginView() {
        this.usermanager = new UserManager();
        this.add(container);
        this.guiInitilaze(800, 400);
        loadUserTable(null);   //user table
        loadUserComponent(); //user login

    }

    public void loadUserTable(ArrayList<Object[]> userList) { //user table
        this.col_user = new Object[]{"Id", "Adı", "Soyadı", "Kullanıcı Adı", "Şifresi", "Rolü"};
        if (userList == null) {
            userList = this.usermanager.getForTable(this.col_user.length, this.usermanager.findAll());
        }
        this.createTable(this.tbl_users_model, this.tbl_user, col_user, userList);

    }

    public void loadUserComponent() { //user login
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField[] checkFieldList = {fld_user_name, fld_user_password};
                if (Helper.isFieldListEmpty(checkFieldList)) {
                    Helper.showMsg("fill");
                } else {
                    User loginUser = usermanager.findByLogin(fld_user_name.getText(), fld_user_password.getText());
                    if (loginUser == null) {
                        Helper.showMsg("notFound");
                    } else {

                        switch (loginUser.getRole()) {
                            case Admin:
                                dispose();
                                AdminView admin = new AdminView(loginUser); //user page
                                break;
                            case Personel:
                                dispose();
                                EmployeeView employee = new EmployeeView(loginUser);   //Employee page
                                break;
                            default:
                                Helper.showMsg("Kullanıcı tipi hatalı!");
                                break;
                        }
                        dispose();
                    }
                }

            }
        });
    }
}

