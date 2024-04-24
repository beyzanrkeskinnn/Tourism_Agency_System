package view;

import business.HotelManager;
import business.PensionManager;
import business.RoomManager;
import business.SeasonManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.Pension;
import entity.Room;
import entity.Season;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RoomView extends Layout {
    private JPanel container;
    private JComboBox cmb_room_hotel;
    private JComboBox cmb_room_pension;
    private JComboBox cmb_room_season;
    private JButton btn_room_save;
    private JTextField tf_adult_price;
    private JTextField tf_child_price;
    private JTextField tf_bed_capacity;
    private JTextField tf_square_meter;
    private JTextField tf_stock;
    private JRadioButton rbut_television;
    private JRadioButton rbut_minibar;
    private JRadioButton rbut_game_console;
    private JComboBox cmb_room_type;
    private JRadioButton rbut_cashbox;
    private JRadioButton rbut_projection;
    private JRadioButton rbut_gym;
    private Hotel hotel;
    private Room room;
    private Season season;
    private ComboItem comboItem;
    private PensionManager pensionManager;
    private SeasonManager seasonManager;
    private HotelManager hotelManager;
    private RoomManager roomManager;

    public RoomView() {
        this.comboItem = new ComboItem();
        this.hotel = new Hotel();
        this.room = new Room();
        this.season = new Season();
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();
        this.hotelManager = new HotelManager();
        this.roomManager = new RoomManager();
        this.add(container);
        this.guiInitilaze(560, 550);

        for (Hotel hotel : hotelManager.findAll()) {
            this.cmb_room_hotel.addItem(hotel.getComboItem());
        }

        cmb_room_hotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ComboItem selectedOtelItem = (ComboItem) cmb_room_hotel.getSelectedItem();
                int selectedOtelId = selectedOtelItem.getKey();
                ArrayList<Pension> pensions = pensionManager.getPensionByOtelId(((ComboItem) cmb_room_hotel.getSelectedItem()).getKey());
                System.out.println("pansiyon: " + pensions);
                cmb_room_pension.removeAllItems();

                for (Pension pension : pensions) {
                    cmb_room_pension.addItem(pension.getComboItem());
                }

                ArrayList<Season> seasons = seasonManager.getSeasonsByOtelId(((ComboItem) cmb_room_hotel.getSelectedItem()).getKey());
                System.out.println("sezonlar: " + seasons);
                cmb_room_season.removeAllItems();
                for (Season season : seasons) {
                    cmb_room_season.addItem(season.getComboItem());

                }

            }
        });
        btn_room_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField[] selectedRoomList = new JTextField[]{tf_adult_price, tf_child_price, tf_bed_capacity, tf_stock, tf_square_meter};

                if (Helper.isFieldListEmpty(selectedRoomList)) {
                    Helper.showMsg("fill");
                } else {
                    boolean result = false;
                    ComboItem selectedHotel = (ComboItem) cmb_room_hotel.getSelectedItem();
                    ComboItem selectedPension = (ComboItem) cmb_room_pension.getSelectedItem();
                    ComboItem selectedSeason = (ComboItem) cmb_room_season.getSelectedItem();
                    room.setSeason_id(selectedSeason.getKey());
                    room.setPension_id(selectedPension.getKey());
                    room.setHotel_id(selectedHotel.getKey());
                    room.setType((String) cmb_room_type.getSelectedItem());
                    room.setStock(Integer.parseInt(tf_stock.getText()));
                    room.setAdult_price(Double.parseDouble(tf_adult_price.getText()));
                    room.setChild_price(Double.parseDouble(tf_child_price.getText()));
                    room.setBed_capacity(Integer.parseInt(tf_bed_capacity.getText()));
                    room.setSquare_meter(Integer.parseInt(tf_square_meter.getText()));
                    room.setTelevision(rbut_television.isSelected());
                    room.setMinibar(rbut_minibar.isSelected());
                    room.setGame_console(rbut_game_console.isSelected());
                    room.setCash_box(rbut_cashbox.isSelected());
                    room.setProjection(rbut_projection.isSelected());

                    if (room.getId() != 0) {
                        result = roomManager.update(room);
                    } else {
                        result = roomManager.save(room);
                    }
                    if (result) {
                        Helper.showMsg("done");


                        dispose();
                    } else {
                        Helper.showMsg("error");
                    }
                }

            }

        });

    }
}

