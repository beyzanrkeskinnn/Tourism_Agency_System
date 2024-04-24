package dao;

import core.Db;
import entity.Reservation;
import entity.Season;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationDao {

    private final Connection connection;

    public ReservationDao() {
        this.connection = Db.getInstance();
    }

    //Method that returns reservations with a specific hotel ID
    public ArrayList<Reservation> getReservationByOtelId(int otelId) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM public.reservation WHERE room_id = ?";

        try (PreparedStatement pr = connection.prepareStatement(query)) {
            pr.setInt(1, otelId);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Reservation reservation = match(rs);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    //Method that retrieves the reservation with a specific ID
    public Reservation getByID(int id) {
        Reservation obj = null;
        String query = "SELECT * FROM public.reservation WHERE reservation_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }


    // Helper method that maps ResultSet to Reservation object
    public Reservation match(ResultSet rs) throws SQLException {
        Reservation obj = new Reservation();
        obj.setId(rs.getInt("reservation_id"));
        obj.setRoom_id(rs.getInt("room_id"));
        obj.setCheck_in_date(LocalDate.parse(rs.getString("check_in_date")));
        obj.setTotal_price(rs.getDouble("total_price"));
        obj.setGuest_count(rs.getInt("guest_count"));
        obj.setGuest_name(rs.getString("guest_name"));
        obj.setGuess_citizen_id(rs.getString("guess_citizen_id"));
        obj.setGuess_mail(rs.getString("guess_mail"));
        obj.setGuess_phone(rs.getString("guess_phone"));
        obj.setCheck_out_date(LocalDate.parse(rs.getString("check_out_date")));
        return obj;
    }

    // Method to fetch all reservations
    public ArrayList<Reservation> findAll() {
        ArrayList<Reservation> resList = new ArrayList<>();
        String sql = "SELECT * FROM public.reservation";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {

                resList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }


    public boolean save(Reservation reservation) {
        String query = "INSERT INTO public.reservation" +
                "(" +
                "room_id," +
                "check_in_date," +
                "total_price," +
                "guest_count," +
                "guest_name," +
                "guess_citizen_id," +
                "guess_mail," +
                "guess_phone," +
                "check_out_date," +
                "adult_count," +
                "child_count" +
                ")" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = connection.prepareStatement(query);

            pr.setInt(1, reservation.getRoom_id());
            pr.setDate(2, Date.valueOf(reservation.getCheck_in_date()));
            pr.setDouble(3, reservation.getTotal_price());
            pr.setInt(4, reservation.getGuest_count());
            pr.setString(5, reservation.getGuest_name());
            pr.setString(6, reservation.getGuess_citizen_id());
            pr.setString(7, reservation.getGuess_mail());
            pr.setString(8, reservation.getGuess_phone());
            pr.setDate(9, Date.valueOf(reservation.getCheck_out_date()));
            pr.setInt(10, reservation.getAdult_count());
            pr.setInt(11, reservation.getChild_count());


            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }


    public boolean delete(int hotel_id) {
        try {
            String query = "DELETE FROM public.reservation WHERE reservation_id = ?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, hotel_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }


    //Method that retrieves reservations with a specific reservation ID
    public ArrayList<Reservation> getByListReservationId(int id) {
        return this.selectByQuery("SELECT * FROM public.reservation WHERE reservation_id=" + id);
    }

    //Method that retrieves reservations with a specific query
    public ArrayList<Reservation> selectByQuery(String query) {//hazır bir SQL sorgu metodu oluşturduk.
        ArrayList<Reservation> resList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                resList.add(this.match(rs));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }


    public boolean update(Reservation reservation) {
        try {
            String query = "UPDATE public.reservation SET " +
                    "room_id = ?," +
                    "check_in_date = ?," +
                    "total_price = ?," +
                    "guest_count = ?," +
                    "guest_name = ?," +
                    "guess_citizen_id = ?," +
                    "guess_mail = ?," +
                    "guess_phone = ?," +
                    "check_out_date = ?," +
                    "adult_count = ?, " +
                    "child_count = ? " +
                    "WHERE reservation_id = ?";

            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, reservation.getRoom_id());
            pr.setDate(2, Date.valueOf(reservation.getCheck_in_date()));
            pr.setDouble(3, reservation.getTotal_price());
            pr.setInt(4, reservation.getGuest_count());
            pr.setString(5, reservation.getGuest_name());
            pr.setString(6, reservation.getGuess_citizen_id());
            pr.setString(7, reservation.getGuess_mail());
            pr.setString(8, reservation.getGuess_phone());
            pr.setDate(9, Date.valueOf(reservation.getCheck_out_date()));
            pr.setInt(10, reservation.getAdult_count());
            pr.setInt(11, reservation.getChild_count());

            pr.setInt(12, reservation.getId());

            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

}