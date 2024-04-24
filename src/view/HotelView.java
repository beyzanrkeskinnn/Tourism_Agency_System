package view;

import business.HotelManager;
import core.Helper;
import entity.Hotel;

import javax.swing.*;

public class HotelView extends Layout {
    private JPanel container;
    private JTextField fld_hotelname;
    private JTextField fld_adres;
    private JTextField fld_mail;
    private JTextField fld_phone;
    private JComboBox cmb_star;
    private JRadioButton radio_carpark;
    private JRadioButton radio_wifi;
    private JRadioButton radio_pool;
    private JRadioButton radio_fitnes;
    private JRadioButton radio_concierge;
    private JRadioButton radio_spa;
    private JRadioButton radio_roomservice;
    private JButton btn_hotel_save;
    private Hotel hotel;
    private HotelManager hotelManager;

    public HotelView(Hotel hotel) { //Hotel added - update page
        this.hotelManager = new HotelManager();
        this.hotel = hotel;
        this.add(container);
        this.guiInitilaze(500, 500);

        if (this.hotel.getId() != 0) { //bring fields full
            this.fld_hotelname.setText(this.hotel.getName());
            this.fld_mail.setText(this.hotel.getMail());
            this.fld_phone.setText(this.hotel.getPhone());
            this.fld_adres.setText(this.hotel.getAddress());
            this.cmb_star.setSelectedItem(this.hotel.getStar());
        }

        btn_hotel_save.addActionListener(e -> {

            JTextField[] checkFieldList = {this.fld_hotelname, this.fld_mail, this.fld_phone, this.fld_adres};

            if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMsg("fill");

            } else {
                boolean result = true;
                this.hotel.setName(this.fld_hotelname.getText());
                this.hotel.setAddress(this.fld_adres.getText());
                this.hotel.setMail(this.fld_mail.getText());
                this.hotel.setPhone(this.fld_phone.getText());
                this.hotel.setStar((String) this.cmb_star.getSelectedItem());
                this.hotel.setCar_park(this.radio_carpark.isSelected());
                this.hotel.setWifi(this.radio_wifi.isSelected());
                this.hotel.setPool(this.radio_pool.isSelected());
                this.hotel.setFitness(this.radio_fitnes.isSelected());
                this.hotel.setConcierge(this.radio_concierge.isSelected());
                this.hotel.setSpa(this.radio_spa.isSelected());
                this.hotel.setRoom_service(this.radio_roomservice.isSelected());

                if (this.hotel.getId() != 0) {
                    result = this.hotelManager.update(this.hotel);
                } else {
                    result = this.hotelManager.save(this.hotel);
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