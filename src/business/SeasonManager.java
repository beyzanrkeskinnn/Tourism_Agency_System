package business;

import core.Helper;
import dao.PensionDao;
import dao.SeasonDao;
import entity.Pension;
import entity.Season;

import java.util.ArrayList;

// Sezon işlemlerini yöneten sınıf
public class SeasonManager {
    SeasonDao seasonDao = new SeasonDao();

    // Belirli bir ID'ye sahip sezonu getiren metot
    public Season getById(int id) {
        return this.seasonDao.getByID(id);
    }

    // Belirli bir otel ID'sine sahip sezonları getiren metot
    public ArrayList<Season> getSeasonsByOtelId(int id) {
        return this.seasonDao.getSeasonsByOtelId(id);
    }

    // Tüm sezonları getiren metot
    public ArrayList<Season> findAll() {
        return this.seasonDao.findAll();
    }

    // Tablo için gerekli bilgileri sağlayan metot
    public ArrayList<Object[]> getForTable(int size, ArrayList<Season> seasons) {
        ArrayList<Object[]> seasonList = new ArrayList<>();
        for (Season obj : seasons) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getHotel().getName();
            rowObject[i++] = obj.getStart_date();
            rowObject[i++] = obj.getFinish_date();
            seasonList.add(rowObject);
        }
        return seasonList;
    }

    // Sezon kaydını veritabanına ekleyen metot
    public boolean save(Season season) {
        if (season.getId() != 0) {
            Helper.showMsg("error");
        }
        return this.seasonDao.save(season);
    }

    // Belirli bir ID'ye sahip sezonu silen metot
    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + " ID kayıtlı model bulunamadı");
            return false;
        }
        return this.seasonDao.delete(id);
    }
}