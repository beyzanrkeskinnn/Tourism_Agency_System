package dao;

import core.Db;
import entity.User;

import javax.management.Query;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private final Connection con;

    public UserDao() {
        this.con = Db.getInstance();
    }

    public ArrayList<User> findAll() { //Call all in the user table query
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM public.user";
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                userList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void tableRowSelect(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(selected_row, selected_row);
            }
        });
    }

    public User findByLogin(String username, String password) {  //Login query with username and password
        User obj = null;
        String query = "SELECT * FROM public.user WHERE user_name= ? AND user_password=? ";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1, username);
            pr.setString(2, password);

            ResultSet rs = pr.executeQuery();
            if (rs.next()) {

                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public User match(ResultSet rs) throws SQLException { //Match with tables in database
        User obj = new User();
        obj.setId(rs.getInt("user_id"));
        obj.setUserFirstName(rs.getString("user_firstname"));
        obj.setUserLastName(rs.getString("user_lastname"));
        obj.setUsername(rs.getString("user_name"));
        obj.setPassword(rs.getString("user_password"));
        obj.setRole(User.Role.valueOf(rs.getString("user_role")));
        return obj;
    }

    public User getById(int id) {
        User object = null;
        String query = "SELECT * FROM public.user WHERE user_id = ? ";
        try {
            PreparedStatement prepared = this.con.prepareStatement(query);
            prepared.setInt(1, id);
            ResultSet resultSet = prepared.executeQuery();

            if (resultSet.next()) {
                object = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return object;
    } //Fetch by user

    public boolean save(User user) { //user added query
        String query = "INSERT INTO public.user " +
                "(user_firstname,user_lastname, user_name, user_password, user_role) " +
                "VALUES (?,?,?,?,?)";
        ;
        try {
            PreparedStatement pr = this.con.prepareStatement(query);

            pr.setString(1, user.getUserFirstName());
            pr.setString(2, user.getUserLastName());
            pr.setString(3, user.getUsername());
            pr.setString(4, user.getPassword());
            pr.setString(5, user.getRole().toString());

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(User user) { //user update query
        String query = "UPDATE public.user SET " +
                "user_firstname=? ," +
                "user_lastname=?," +
                "user_name=? , " +
                "user_password=? ," +
                "user_role=? " +
                "WHERE user_id=?";

        try {
            PreparedStatement prepared = this.con.prepareStatement(query);
            prepared.setString(1, user.getUserFirstName());
            prepared.setString(2, user.getUserLastName());
            prepared.setString(3, user.getUsername());
            prepared.setString(4, user.getPassword());
            prepared.setString(5, user.getRole().toString());
            prepared.setInt(6, user.getId());


            return prepared.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM public.user WHERE user_id = ?";
        try {
            PreparedStatement prepared = this.con.prepareStatement(query);
            prepared.setInt(1, id);
            return prepared.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public ArrayList<User> selectByQuery(String query) {
        ArrayList<User> modelList = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()) {
                modelList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelList;
    }
}
