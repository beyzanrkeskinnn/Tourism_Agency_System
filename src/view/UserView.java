package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

public class UserView extends Layout {
    private UserManager userManager;
    private  User user;
    private JPanel container;
    private JLabel fld_user_name;
    private JTextField fld_user_firstname;
    private JTextField fld_user_lastname;
    private JTextField fld_username_View;
    private JTextField fld_userview_Password;
    private JComboBox<User.Role> cmb_user_rol;
    private JButton btn_user_save;

    public UserView(User user) { //User added - update page
        this.userManager = new UserManager();
        this.user = user;
        this.add(container);
        this.guiInitilaze(500, 500);
        if (user != null) {
            fld_username_View.setText(user.getUsername());
        }

        this.cmb_user_rol.setModel(new DefaultComboBoxModel<>(User.Role.values())); //comboları listeler
        if (this.user.getId() != 0) {
            System.out.println("id alındı");
            this.fld_user_firstname.setText(this.user.getUserFirstName());
            this.fld_user_lastname.setText(this.user.getUserLastName());
            this.fld_username_View.setText(this.user.getUsername());
            this.fld_userview_Password.setText(this.user.getPassword());
            this.cmb_user_rol.getModel().setSelectedItem(this.user.getRole());
        }

        btn_user_save.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.fld_username_View)) {
                Helper.showMsg("fill");
            } else {
                boolean result;
                this.user.setUserFirstName(this.fld_user_firstname.getText());
                this.user.setUserLastName(this.fld_user_lastname.getText());
                this.user.setUsername(this.fld_username_View.getText());
                this.user.setPassword(this.fld_userview_Password.getText());
                this.user.setRole((User.Role) this.cmb_user_rol.getSelectedItem());
                if (this.user.getId() != 0) {
                    result = this.userManager.update(this.user);
                } else {
                    result = this.userManager.save(this.user);
                }
                if (result) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }
}

