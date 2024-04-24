package business;

import core.Helper;
import dao.UserDao;
import entity.User;

import java.util.ArrayList;

public class UserManager {
    private final UserDao userDao;

    public UserManager() {
        this.userDao = new UserDao();
    }

    public User findByLogin(String username, String password) {
        return this.userDao.findByLogin(username, password);
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<User> userList) {
        ArrayList<Object[]> userRowList = new ArrayList<>();
        for (User user : userList) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = user.getId();
            rowObject[i++] = user.getUserFirstName();
            rowObject[i++] = user.getUserLastName();
            rowObject[i++] = user.getUsername();
            rowObject[i++] = user.getPassword();
            rowObject[i++] = user.getRole();


            userRowList.add(rowObject);
        }
        return userRowList;
    }

    public ArrayList<User> findAll() {

        return this.userDao.findAll();
    }

    public User getById(int id) {
        return this.userDao.getById(id);
    }

    public boolean save(User user) {
        if (this.getById(user.getId()) != null) {
            Helper.showMsg("Error: Model with ID " + user.getId() + " already exists.");
            return false;
        }
        return this.userDao.save(user);
    }

    public boolean update(User user) {
        if (this.getById(user.getId()) == null) {
            System.out.println("hatadır");
            Helper.showMsg("notFound");
        }
        return this.userDao.update(user);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + "ID kayıtlı marka bulunamadı");
            return false;
        }
//        for (Model model : this.modelManager.getByListBrandId(id)) {
//            this.modelManager.delete(model.getId());
//        }
        return this.userDao.delete(id);
    }

    public ArrayList<User> searchForTable(User.Role role) {
        String select = "SELECT * FROM public.user";
        ArrayList<String> whereList = new ArrayList<>();

        if (role != null) {
            whereList.add("user_role = '" + role.toString() + "'");
        }

        String whereStr = String.join(" AND ", whereList);
        String query = select;
        if (!whereStr.isEmpty()) {
            query += " WHERE " + whereStr;
        }
        return this.userDao.selectByQuery(query);
    }
}
