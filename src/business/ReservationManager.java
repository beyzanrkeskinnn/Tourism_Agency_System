package business;

import core.Helper;
import dao.ReservationDao;
import dao.SeasonDao;
import entity.Reservation;
import entity.Season;

import java.util.ArrayList;

// Rezervasyon işlemlerini yöneten sınıf
public class ReservationManager {
    ReservationDao reservationDao = new ReservationDao();

    // Belirli bir ID'ye sahip rezervasyonu getiren metot
    public Reservation getById(int id) {
        return this.reservationDao.getByID(id);
    }

    // Belirli bir otel ID'sine sahip rezervasyonları getiren metot
    public ArrayList<Reservation> getReservationByOtelId(int id) {
        return this.reservationDao.getReservationByOtelId(id);
    }

    // Tüm rezervasyonları getiren metot
    public ArrayList<Reservation> findAll() {
        return this.reservationDao.findAll();
    }

    // Tablo için gerekli bilgileri sağlayan metot
    public ArrayList<Object[]> getForTable(int size, ArrayList<Reservation> reservations) {
        ArrayList<Object[]> resList = new ArrayList<>();
        for (Reservation obj : reservations) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getRoom_id();
            rowObject[i++] = obj.getCheck_in_date();
            rowObject[i++] = obj.getCheck_out_date();
            rowObject[i++] = obj.getTotal_price();
            rowObject[i++] = obj.getGuest_count();
            rowObject[i++] = obj.getGuest_name();
            rowObject[i++] = obj.getGuess_citizen_id();
            rowObject[i++] = obj.getGuess_mail();
            rowObject[i++] = obj.getGuess_phone();

            resList.add(rowObject);
        }
        return resList;
    }

    // Rezervasyon kaydını veritabanına ekleyen metot
    public boolean save(Reservation reservation) {
        if (reservation.getId() != 0) {
            Helper.showMsg("error");
        }
        return this.reservationDao.save(reservation);
    }


    // Belirli bir ID'ye sahip rezervasyonu silen metot
    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(" Rezervasyon bulunamadı");
            return false;
        }
        for (Reservation reservation : this.reservationDao.getByListReservationId(id)) {
            this.reservationDao.delete(reservation.getId());
        }
        return this.reservationDao.delete(id);
    }

    // Rezervasyon bilgilerini güncelleyen metot

    public boolean update(Reservation reservation) {
        if (this.getById(reservation.getId()) == null) {
            Helper.showMsg("Reservasyon " + reservation.getId() + "Bulunamadı");
            return false;
        }
        return this.reservationDao.update(reservation);
    }

}