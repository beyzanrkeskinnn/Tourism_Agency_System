package view;

import business.HotelManager;
import business.SeasonManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.Season;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SeasonView extends Layout {
    private JPanel container;
    private JComboBox cmb_hotel;
    private JTextField fld_startDate;
    private JTextField fld_finishDate;
    private JFormattedTextField tfld_startDate;
    private JFormattedTextField tfld_finishDate;
    private JButton btn_save_season;
    private HotelManager hotelManager;
    private Hotel hotel;
    private SeasonManager seasonManager;
    private Season season;
    public SeasonView(int hotel_id)  {
        this.hotelManager = new HotelManager();
        this.hotel = new Hotel();
        this.seasonManager = new SeasonManager();
        this.season = new Season();
        this.cmb_hotel.getSelectedItem();
        this.add(container);
        this.guiInitilaze(375, 300);



        for (Hotel hotel : this.hotelManager.findAll()) {
            this.cmb_hotel.addItem(hotel.getComboItem());
        }
        btn_save_season.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = false;
                ComboItem selectSeason = (ComboItem) cmb_hotel.getSelectedItem();
                season.setHotel_id(selectSeason.getKey());
                season.setSeason_type(cmb_hotel.getSelectedItem().toString());
                JFormattedTextField[] checkDateList = {tfld_startDate, tfld_finishDate};
                if (Helper.isFieldListEmpty(checkDateList)) {
                    Helper.showMsg("fill");
                } else {
                    try {

                        season.setStart_date(LocalDate.parse(tfld_startDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        season.setFinish_date(LocalDate.parse(tfld_finishDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                        result = seasonManager.save(season);

                    } catch (DateTimeException ex) {
                        Helper.showMsg("Date Format is Wrong !");
                        return;
                    }
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


    private void createUIComponents() throws ParseException {
        this.tfld_startDate = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.tfld_startDate.setText("01/06/2024");
        this.tfld_finishDate = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.tfld_finishDate.setText("01/12/2024");
    }
    }

