package business;

import core.Helper;
import dao.RoomDao;
import entity.Pension;
import entity.Room;
import entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


// Oda işlemlerini yöneten sınıf
public class RoomManager {
    RoomDao roomDao = new RoomDao(); // Oda veritabanı erişim nesnesi

    // Belirli bir ID'ye sahip odayı getiren metot
    public Room getById(int id) {
        return this.roomDao.getByID(id);
    }

    // Tüm odaları getiren metot
    public ArrayList<Room> findAll() {
        return this.roomDao.findAll();
    }

    // Tablo için gerekli bilgileri sağlayan metot
    public ArrayList<Object[]> getForTable(int size, ArrayList<Room> rooms) {
        ArrayList<Object[]> roomList = new ArrayList<>();
        for (Room obj : rooms) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getHotel().getName();
            rowObject[i++] = obj.getPension().getPension_type();
            rowObject[i++] = obj.getSeason_id();
            rowObject[i++] = obj.getType();
            rowObject[i++] = obj.getStock();
            rowObject[i++] = obj.getAdult_price();
            rowObject[i++] = obj.getChild_price();
            rowObject[i++] = obj.getBed_capacity();
            rowObject[i++] = obj.getSquare_meter();
            rowObject[i++] = obj.isTelevision();
            rowObject[i++] = obj.isMinibar();
            rowObject[i++] = obj.isGame_console();
            rowObject[i++] = obj.isCash_box();
            rowObject[i++] = obj.isProjection();
            roomList.add(rowObject);
        }
        return roomList;
    }

    // Oda kaydını veritabanına ekleyen metot
    public boolean save(Room room) {
        if (room.getId() != 0) {
            Helper.showMsg("error");
        }
        return this.roomDao.save(room);
    }

    // Oda stokunu güncelleyen metot
    public boolean updateStock(Room room) {
        if (this.getById(room.getId()) == null) {
            return false;
        }
        return this.roomDao.updateStock(room);
    }

    // Belirli bir ID'ye sahip odayı silen metot
    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + " ID kayıtlı  bulunamadı");
            return false;
        }
        return this.roomDao.delete(id);
    }

    // Oda bilgilerini güncelleyen metot
    public boolean update(Room room) {
        if (this.getById(room.getId()) == null) {
            Helper.showMsg(room.getId() + " ID kayıtlı  bulunamadı");
            return false;
        }
        return this.roomDao.update(room);
    }


    // Arama kriterlerine göre odaları filtreleyen metot
    // Oda arama metod
    public ArrayList<Room> searchForRoom(String hotel_name, String hotel_address, String strt_date, String fnsh_date, String adult_num, String child_num) {

        // SQL sorgusunu oluştur
        String query = "SELECT * FROM room r " +
                "JOIN hotel h ON r.hotel_id = h.hotel_id " +
                "LEFT JOIN season s ON r.season_id = s.season_id";

        // WHERE ve JOIN ON şartlarını tutacak ArrayList'ler
        ArrayList<String> where = new ArrayList<>();
        ArrayList<String> joinWhere = new ArrayList<>();

        // Tarih formatını uygun formata çevir
        strt_date = LocalDate.parse(strt_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
        fnsh_date = LocalDate.parse(fnsh_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();

        // Otel adı şartı ekle
        if (hotel_name != null) {
            where.add("h.hotel_name ILIKE '%" + hotel_name + "%'");
        }

        // Otelin şehirlerini şartı ekle
        if (hotel_address != null) {
            where.add("h.hotel_adresses ILIKE '%" + hotel_address + "%'");
        }

        // Yetişkin ve çocuk sayısına göre şartları ekle
        if (adult_num != null && !adult_num.isEmpty() && child_num != null && !child_num.isEmpty()) {
            try {
                int adultNum = Integer.parseInt(adult_num);
                int childNum = Integer.parseInt(child_num);
                int total_person = adultNum + childNum;
                where.add("r.bed_capacity >= '" + (total_person) + "'");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Sezon tarihleri şartlarını ekle
        where.add("(s.start_date <= '" + strt_date + "')");
        where.add("(s.finish_date >= '" + fnsh_date + "')");

        // Oda stoğu kontrolü şartını ekle
        where.add("r.stock > 0");

        // WHERE şartlarını birleştir
        String whereStr = String.join(" AND ", where);
        String joinStr = String.join(" AND ", joinWhere);

        // JOIN ON şartını sorguya ekle
        if (joinStr.length() > 0) {
            query += " ON " + joinStr;
        }

        // WHERE şartını sorguya ekle
        if (whereStr.length() > 0) {
            query += " WHERE " + whereStr;
        }

        // Oluşturulan sorguyu ekrana yazdır (opsiyonel, sorguyu kontrol etmek için)
        System.out.println(query);

        // Oluşturulan sorguya göre odaları getir
        return this.roomDao.selectByQuery(query);
    }

    // Belirli bir oda ID'si ile odayı getiren metod

}