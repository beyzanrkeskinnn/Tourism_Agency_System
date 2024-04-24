package view;

import business.ReservationManager;
import business.RoomManager;
import core.Helper;
import entity.Reservation;
import entity.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


// ReservationAddView sınıfı, rezervasyon ekleme ve güncelleme arayüzünü temsil eder ve Layout sınıfından türetilmiştir.
public class ReservationView extends Layout {

    // Arayüzdeki bileşenlerin tanımlandığı değişkenler.
    private JPanel container;
    private JTextField fld_reservation_hotel_name;
    private JTextField fld_reservation_address;
    private JTextField fld_reservation_star;
    private JRadioButton radio_carpark;
    private JRadioButton radio_concierge;
    private JRadioButton radio_wifi;
    private JRadioButton radio_spa;
    private JRadioButton radio_pool;
    private JRadioButton radio_room_service;
    private JRadioButton radio_fitness;
    private JTextField fld_roomType;
    private JTextField fld_pansiyonType;
    private JTextField fld_startDate;
    private JTextField fld_endDate;
    private JTextField fld_totalPrice;
    private JTextField fld_bedCapacity;
    private JTextField fld_meter;
    private JRadioButton radio_tv;
    private JRadioButton radio_gameConsole;
    private JRadioButton radio_projection;
    private JRadioButton radio_cashBox;
    private JRadioButton radio_minibar;
    private JTextField fld_guestName;
    private JTextField fld_guestPhone;
    private JTextField fld_guest_total_person;
    private JTextField fld_guestMail;
    private JButton btn_reservationSve;
    private JTextField fld_guestID;
    private JTextField fld_hotel_adult;
    private JTextField fld_hotel_child;
    private Reservation reservation;
    private ReservationManager reservationManager;
    private RoomManager roomManager;
    private Room room;
    private EmployeeView employeeView;

    // ReservationAddView sınıfının constructor'ı. Rezervasyon ve oda bilgileri ile başlatılır.
    public ReservationView(Reservation reservation, Room room, JFormattedTextField startDate, JFormattedTextField endDate, JTextField adult, JTextField child) {
        //Reservation reservation = convertRoomToReservation(room);
        this.room = room;
        this.reservation = reservation;
        this.reservationManager = new ReservationManager();
        this.add(container);
        this.guiInitilaze(1000, 700);
        this.roomManager = new RoomManager();
        String strGuestNumber;
        LocalDate from;
        LocalDate to;
        double totalPrice;

        // Rezervasyon ID'si 0 değilse (yani mevcut bir rezervasyon güncelleniyorsa), mevcut rezervasyon bilgileri kullanılır.
        if (this.reservation.getId() != 0) {
            from = this.reservation.getCheck_in_date();
            to = this.reservation.getCheck_out_date();
            strGuestNumber = String.valueOf(this.reservation.getGuest_count());
            this.fld_guest_total_person.setText(strGuestNumber);
            this.fld_guestName.setText(this.reservation.getGuest_name());
            this.fld_guestID.setText(String.valueOf(this.reservation.getGuess_citizen_id()));
            this.fld_guestMail.setText(this.reservation.getGuess_mail());
            this.fld_guestPhone.setText((this.reservation.getGuess_phone()));


            totalPrice = this.reservation.getTotal_price();

        } else {

            // Yeni bir rezervasyon ekleniyorsa, giriş ve çıkış tarihleri, yetişkin ve çocuk sayısı gibi bilgiler kullanılır.
            from = LocalDate.parse(startDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            to = LocalDate.parse(endDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (child.getText().isEmpty()) {
                child.setText("0");
            }
            if (adult.getText().isEmpty()) {
                adult.setText("0");
            }
            int adultCount = Integer.parseInt(adult.getText());
            int childCount = Integer.parseInt(child.getText());
            int totalGuestNumber = adultCount + childCount;
            strGuestNumber = String.valueOf(totalGuestNumber);
            this.fld_guest_total_person.setText(strGuestNumber);
            long daysBetween = ChronoUnit.DAYS.between(from, to);
            double adultPrice = room.getAdult_price();
            double childPrice = room.getChild_price();
            totalPrice = ((adultPrice * adultCount) + (childPrice * childCount)) * daysBetween;
        }

        // Arayüzdeki bileşenlere otel ve oda bilgileri set edilir.
        this.fld_reservation_hotel_name.setText(room.getHotel().getName());
        this.fld_reservation_address.setText(room.getHotel().getAddress());
        this.fld_reservation_star.setText(room.getHotel().getStar());
        this.fld_roomType.setText(room.getType());
        this.fld_pansiyonType.setText(room.getPension().getPension_type());
        this.fld_startDate.setText(from.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.fld_endDate.setText(to.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.fld_totalPrice.setText(String.valueOf(totalPrice));
        this.fld_meter.setText(String.valueOf(room.getSquare_meter()));
        this.fld_bedCapacity.setText(String.valueOf(room.getBed_capacity()));

        // Otel özellikleri için radio button'lar kontrol edilir ve set edilir.
        this.radio_concierge.setSelected(room.getHotel().isConcierge());
        this.radio_carpark.setSelected(room.getHotel().isCar_park());
        this.radio_fitness.setSelected(room.getHotel().isFitness());
        this.radio_wifi.setSelected(room.getHotel().isWifi());
        this.radio_spa.setSelected(room.getHotel().isSpa());
        this.radio_pool.setSelected(room.getHotel().isPool());
        this.radio_room_service.setSelected(room.getHotel().isRoom_service());
        this.radio_tv.setSelected(room.isTelevision());
        this.radio_gameConsole.setSelected(room.isGame_console());
        this.radio_projection.setSelected(room.isProjection());
        this.radio_minibar.setSelected(room.isMinibar());
        this.radio_cashBox.setSelected(room.isCash_box());


        // "btn_reservationSve" butonuna ActionListener eklenir.
        btn_reservationSve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Gerekli text alanları kontrol edilir ve gerekli işlemler yapılır.
                JTextField[] checkFieldList = {fld_guestName, fld_guestID, fld_guestMail, fld_guestPhone, fld_startDate, fld_endDate};
                if (Helper.isFieldListEmpty(checkFieldList)) {
                    Helper.showMsg("fill");
                } else {
                    boolean result = true;
                    reservation.setRoom_id(room.getId());
                    reservation.setGuest_name(fld_guestName.getText());
                    reservation.setGuess_citizen_id(fld_guestID.getText());
                    reservation.setGuess_mail(fld_guestMail.getText());
                    reservation.setGuess_phone(fld_guestPhone.getText());
                    reservation.setCheck_in_date(LocalDate.parse(startDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    reservation.setCheck_out_date(LocalDate.parse(endDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    reservation.setTotal_price((int) totalPrice);
                    reservation.setGuest_count(Integer.parseInt(strGuestNumber));


                    // Rezervasyon ID'si 0 değilse (yani mevcut bir rezervasyon güncelleniyorsa), güncelleme yapılır.
                    if (reservation.getId() != 0) {
                        result = reservationManager.update(reservation);
                        dispose();
                    } else {

                        // Yeni bir rezervasyon ekleniyorsa, rezervasyon kaydedilir ve oda stoku güncellenir.
                        result = reservationManager.save(reservation);
                        room.setStock(room.getStock() - 1);
                        roomManager.updateStock(room);
                        dispose();
                    }
                    if (result) {
                        Helper.showMsg("done");


                    } else {
                        Helper.showMsg("error");
                    }

                }
            }
        });
    }

}