package view;

import business.HotelManager;
import business.PensionManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.Pension;

import javax.swing.*;

public class PensionView extends Layout {
    private JPanel container;
    private JComboBox cmb_hotel;
    private JComboBox cmb_pension;
    private JButton btn_save_pension;
    private Hotel hotel;
    private HotelManager hotelManager;
    private PensionManager pensionManager;
    private Pension pension;

    public PensionView() {

    }

    public PensionView(int hotel_id) { //pension added page

        this.hotel = new Hotel();
        this.hotelManager = new HotelManager();
        this.pensionManager = new PensionManager();
        this.pension = new Pension();
        this.add(container);
        this.guiInitilaze(375, 250);

        for (Hotel hotel : this.hotelManager.findAll()) { //hotel list
            this.cmb_hotel.addItem(hotel.getComboItem());
        }

        this.btn_save_pension.addActionListener(e -> {
            boolean result = false;
            ComboItem selectedHotel = (ComboItem) cmb_hotel.getSelectedItem();
            this.pension.setHotel_id(selectedHotel.getKey());
            this.pension.setPension_type(cmb_pension.getSelectedItem().toString());

            if (this.pension.getId() != 0) {
                result = this.pensionManager.update(this.pension);

            } else {
                result = this.pensionManager.save(this.pension);
            }
            if (result) {
                Helper.showMsg("done");
                dispose();
            } else {
                Helper.showMsg("error");
            }
        });
    }
}
