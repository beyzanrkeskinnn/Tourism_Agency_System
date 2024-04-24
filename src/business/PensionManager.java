package business;

import core.Helper;
import dao.PensionDao;
import entity.Hotel;
import entity.Pension;
//import entity.Season;
import entity.User;

import java.util.ArrayList;


// Pansiyon işlemlerini yöneten sınıf
public class PensionManager {
    PensionDao pensionDao = new PensionDao();

    // Belirli bir ID'ye sahip pansiyonu getiren metot
    public Pension getById(int id) {
        return this.pensionDao.getByID(id);
    }

    // Tüm pansiyonları getiren metot
    public ArrayList<Pension> findAll() {
        return this.pensionDao.findAll();
    }

    // Belirli bir otel ID'sine sahip pansiyonları getiren metot
    public ArrayList<Pension> getPensionByOtelId(int id) {
        return this.pensionDao.getPensionByOtelId(id);
    }

    // Tablo için gerekli bilgileri sağlayan metot
    public ArrayList<Object[]> getForTable(int size, ArrayList<Pension> pensions) {
        ArrayList<Object[]> pensionList = new ArrayList<>();
        for (Pension obj : pensions) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getHotel().getName();
            rowObject[i++] = obj.getPension_type();
            pensionList.add(rowObject);
        }
        return pensionList;
    }


    // Pansiyon kaydını veritabanına ekleyen metot
    public boolean save(Pension pension) {
        if (pension.getId() != 0) {
            Helper.showMsg("error");
        }
        return this.pensionDao.save(pension);
    }

    // Pansiyon bilgilerini güncelleyen metot
    public boolean update(Pension pension) {
        if (this.getById(pension.getId()) == null) {
            Helper.showMsg(pension.getId() + "ID kayıtlı model bulunamadı");
            return false;
        }
        return this.pensionDao.update(pension);
    }

    // Belirli bir ID'ye sahip pansiyonu silen metot
    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + " ID kayıtlı model bulunamadı");
            return false;
        }
        return this.pensionDao.delete(id);
    }
}