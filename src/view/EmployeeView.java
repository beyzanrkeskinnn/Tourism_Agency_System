package view;

import business.*;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JTable tbl_hotel;
    private JButton btn_loading;
    private JButton btnsave_hotel;
    private JTable tbl_pension;
    private JTable tbl_season;
    private JButton btn_season_save;
    private JButton btn_pension_save;
    private JTabbedPane room_panel;
    private JTable tbl_room;
    private JButton btn_room_add;
    private JTextField fld_hotel_adult;
    private JTextField fld_hotel_child;
    private JTextField fld_hotel_city;
    private JTextField fld_hotel_name;

    private JButton btn_search_room;
    private JTable tbl_reservation;
    private JButton btn_save_reservation;
    private JFormattedTextField fld_hotel_strt_date;
    private JFormattedTextField fld_hotel_fnsh_date;
    private JButton btn_room_clear;
    private JComboBox cmb_hotel_name;
    private JComboBox cmb_hotel_city;
    private UserManager userManager;
    private HotelManager hotelManager;
    private PensionManager pensionManager;
    private SeasonManager seasonManager;
    private RoomManager roomManager;
    private ReservationManager reservationManager;
    private User user;
    private Object[] col_hotel;
    private Object[] col_room;
    private Object[] col_pension;
    private DefaultTableModel tbl_hotels_model = new DefaultTableModel();
    private DefaultTableModel tbl_pension_model = new DefaultTableModel();
    private DefaultTableModel tbl_season_model = new DefaultTableModel();
    private DefaultTableModel tbl_room_model = new DefaultTableModel();
    private DefaultTableModel tbl_reservation_model = new DefaultTableModel();
    private JPopupMenu hotel_menu;
    private JPopupMenu pension_menu;
    private JPopupMenu season_menu;
    private JPopupMenu room_menu;
    private JPopupMenu reservation_menu;

    private Pension pension;

    public JTable getTbl_room() {
        return tbl_room;
    }

    public void setTbl_room(JTable tbl_room) {
        this.tbl_room = tbl_room;
    }

    public EmployeeView(User user) {
        this.userManager = new UserManager();
        this.hotelManager = new HotelManager();
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();
        this.roomManager = new RoomManager();
        this.reservationManager = new ReservationManager();
        this.pension = new Pension();
        this.add(container);
        this.guiInitilaze(1400, 700);
        this.user = user;
        if (user == null) {
            dispose();
        }
        this.lbl_welcome.setText("Hoşgeldiniz : " + this.user.getUsername());
        loadComponent();
        loadHotelTable(null);
        loadHotelSave();
        loadHotelTableComponent();
        loadPensionTable(null);
        LoadPensionTableComponent();
        loadSeasonTable();
        loadSeasonTableComponent();
        loadRoomTable(null);
        loadRoomComponent();
        loadReservationTable(null);
        LoadReservationTableComponent();
        tableRowSelect(tbl_hotel);
        tableRowSelect(tbl_pension);
        tableRowSelect(tbl_room);


    }

    private boolean isNumeric(String strNum) {

        // Verilen string bir sayıya dönüştürülebilir mi kontrol ediliyor.
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void loadComponent() {
        this.btn_loading.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView loginView = new LoginView();
            }
        });
    }

    public void loadHotelTable(ArrayList<Object[]> hotelList) {
        this.col_hotel = new Object[]{"ID", "Name", "Adress", "Mail", "Telefon", "Star", "Car Park", "Wifi", "Pool", "Fitness", "Concierge", "Spa", "Room Services"};
        if (hotelList == null) {
            hotelList = this.hotelManager.getForTable(this.col_hotel.length, this.hotelManager.findAll());
        }
        this.createTable(this.tbl_hotels_model, this.tbl_hotel, col_hotel, hotelList);


    }

    public void loadHotelSave() {
        btnsave_hotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HotelView hotelView = new HotelView(new Hotel());
                tableRowSelect(tbl_hotel);
                loadHotelTable(null);
            }

        });
    }

    public void loadHotelTableComponent() {
        tableRowSelect(tbl_hotel);
        this.hotel_menu = new JPopupMenu();
        this.hotel_menu.add("Ekle").addActionListener(e -> {
            HotelView hotelView = new HotelView(new Hotel());
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable(null);

                }
            });
        });
        hotel_menu.add("Güncelle").addActionListener(e -> {
            tableRowSelect(tbl_hotel);
            int selectHotelId = this.getTableSelectedRow(tbl_hotel, 0);
            HotelView hotelView = new HotelView(this.hotelManager.getById(selectHotelId));
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable(null);
                    loadPensionTable(null);
                    loadSeasonTable();
                    loadRoomTable(null);
                    loadReservationTable(null);
                }
            });
        });
        this.hotel_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectModelId = this.getTableSelectedRow(tbl_hotel, 0);
                if (this.hotelManager.delete(selectModelId)) {
                    Helper.showMsg("done");
                    loadHotelTable(null);
                    loadPensionTable(null);
                    loadSeasonTable();
                    loadRoomTable(null);
                    loadReservationTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.hotel_menu.add("Pension ADD").addActionListener(e -> {
            int selectId = this.getTableSelectedRow(tbl_hotel, 0);
            PensionView pensionview = new PensionView(selectId);
            pensionview.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPensionTable(null);

                }
            });
        });


        this.tbl_hotel.setComponentPopupMenu(hotel_menu);
    }

    public void loadPensionTable(ArrayList<Object[]> pensionList) {
        this.col_pension = new Object[]{"ID", "Hotel ID", "Pension Type"};
        if (pensionList == null) {
            pensionList = this.pensionManager.getForTable(col_pension.length, this.pensionManager.findAll());
        }
        createTable(this.tbl_pension_model, tbl_pension, col_pension, pensionList);
    }

    public void LoadPensionTableComponent() {
        tableRowSelect(tbl_pension);
        btn_pension_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectId = tbl_pension.getSelectedRow();
                PensionView pensionview = new PensionView(selectId);
                tableRowSelect(tbl_pension);
                loadPensionTable(null);
            }
        });
        this.pension_menu = new JPopupMenu();
        this.pension_menu.add("Ekle").addActionListener(e -> {
            int selectId = this.getTableSelectedRow(tbl_pension, 0);
            PensionView pensionview = new PensionView(selectId);
            pensionview.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPensionTable(null);
                }
            });
        });
        pension_menu.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(tbl_pension, 0);
                if (this.pensionManager.delete(selectBrandId)) {
                    Helper.showMsg("done");
                    loadPensionTable(null);


                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_pension.setComponentPopupMenu(pension_menu);
    }

    public void loadSeasonTable() {
        Object[] col_season = {"ID", "Hotel ID", "Start Date", "Finish Date"};
        ArrayList<Object[]> seasonList = this.seasonManager.getForTable(col_season.length, this.seasonManager.findAll());
        createTable(this.tbl_season_model, tbl_season, col_season, seasonList);

    }

    public void loadSeasonTableComponent() {
        tableRowSelect(tbl_season);
        btn_season_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectId = tbl_season.getSelectedRow();
                SeasonView seasonView = new SeasonView(selectId);
                loadSeasonTable();
            }
        });

        this.season_menu = new JPopupMenu();
        this.season_menu.add("Ekle").addActionListener(e -> {
            int selectId = this.getTableSelectedRow(tbl_season, 0);
            SeasonView seasonView = new SeasonView(selectId);
            seasonView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadSeasonTable();
                }
            });
        });
        season_menu.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectSeasonId = this.getTableSelectedRow(tbl_season, 0);
                if (this.seasonManager.delete(selectSeasonId)) {
                    Helper.showMsg("done");
                    loadSeasonTable();

                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_season.setComponentPopupMenu(season_menu);
    }

    public void loadRoomTable(ArrayList<Object[]> roomList) {
        col_room = new Object[]{"ID", "Hotel ID", "Pension ID", "Season ID", "Type", "Stock", "Adult Price", "Child Price", "Bed Capacity", "Square Meter", "Television", "Minibar", "Game Console", "Cash BOX", "Projection"};
        if (roomList == null) {
            roomList = roomManager.getForTable(col_room.length, this.roomManager.findAll());
        }
        createTable(this.tbl_room_model, tbl_room, col_room, roomList);
    }

    public void loadRoomComponent() {
        tableRowSelect(tbl_room);
        btn_room_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoomView roomView = new RoomView();
                loadRoomTable(null);
            }

        });
        this.btn_room_clear.addActionListener(e -> {

            // Formdaki metin alanları sıfırlanıyor.
            this.fld_hotel_name.setText("");
            this.fld_hotel_strt_date.setText("");
            this.fld_hotel_fnsh_date.setText("");
            this.fld_hotel_city.setText("");
            this.fld_hotel_adult.setText("");
            this.fld_hotel_child.setText("");
            loadRoomTable(null);
        });

        btn_search_room.addActionListener(e -> {

            // Formdaki metin alanlarından değerler alınıyor.
            String adultPriceText = (fld_hotel_adult.getText());
            String childPriceText = (fld_hotel_child.getText());
            if (!isNumeric(adultPriceText)) {
                // Eğer adultNumText bir sayı değilse, kullanıcıya bir hata mesajı göster
                Helper.showMsg(adultPriceText + " Geçerli bir sayı değil.");
                return; // Fonksiyonu burada sonlandır, çünkü devam etmek anlamsız olacaktır.
            }
            if (!isNumeric(childPriceText)) {
                Helper.showMsg(childPriceText + " Geçerli bir sayı değil.");
                return;
            }

            ArrayList<Room> roomList = this.roomManager.searchForRoom(
                    // Oda arama işlemi yapılıyor.
                    fld_hotel_name.getText(),
                    fld_hotel_city.getText(),
                    fld_hotel_strt_date.getText(),
                    fld_hotel_fnsh_date.getText(),
                    fld_hotel_adult.getText(),
                    fld_hotel_child.getText()
            );
            System.out.println(roomList);
            ArrayList<Object[]> roomRow = this.roomManager.getForTable(this.col_room.length, roomList);

            // Oda tablosu güncelleniyor.
            loadRoomTable(roomRow);
        });

        this.room_menu = new JPopupMenu();
        room_menu.add("Oda Ekle").addActionListener(e -> {
            RoomView roomView = new RoomView();
            loadRoomTable(null);
        });

        room_menu.add("Reservation Ekle").addActionListener(e -> {
            int selectId = this.getTableSelectedRow(tbl_room, 0);
            JTextField[] roomJTextField = new JTextField[]{fld_hotel_strt_date, fld_hotel_fnsh_date, fld_hotel_adult, fld_hotel_child};
            if (Helper.isFieldListEmpty(roomJTextField)) {
                Helper.showMsg("Rezervasyon eklemek için yetişkin - çocuk - giriş ve çıkış tarihini girmeniz gerekiyor !");
            } else {
                int adult_numb = Integer.parseInt(this.fld_hotel_adult.getText());
                int child_numb = Integer.parseInt(this.fld_hotel_child.getText());
                int selectedId = getTableSelectedRow(tbl_room, 0);
                ReservationView reservationGUI = new ReservationView(new Reservation(),
                        roomManager.getById(selectedId),
                        this.fld_hotel_strt_date,
                        this.fld_hotel_fnsh_date,
                        this.fld_hotel_adult,
                        this.fld_hotel_child);
                reservationGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadRoomTable(null);
                        loadReservationTable(null);
                    }
                });
            }

        });


        room_menu.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectRoomId = this.getTableSelectedRow(tbl_room, 0);
                if (this.roomManager.delete(selectRoomId)) {
                    Helper.showMsg("done");
                    loadRoomTable(null);

                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_room.setComponentPopupMenu(room_menu);

        btn_search_room.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField[] roomJTextField = new JTextField[]{fld_hotel_adult, fld_hotel_child};
                if (Helper.isFieldListEmpty(roomJTextField)) {// Filtreleme aşamasında çocuk veya yetişkin sayısını girmezse hata mesajı ver
                    Helper.showMsg("Please enter the number of children and adults");
                } else {
                    int selectedAdult = Integer.parseInt(fld_hotel_adult.getText());
                    int selectedChild = Integer.parseInt(fld_hotel_child.getText());

                    if (selectedAdult < 0 || selectedChild < 0) {
                        Helper.showMsg("Please enter the number of children and adults greater than 0");
                    }
                    ArrayList<Room> roomList = roomManager.searchForRoom(
                            fld_hotel_city.getText(),
                            fld_hotel_name.getText(),
                            fld_hotel_strt_date.getText(),
                            fld_hotel_fnsh_date.getText(),
                            fld_hotel_adult.getText(),
                            fld_hotel_child.getText()
                    );

                    ArrayList<Object[]> searchResult = roomManager.getForTable(col_room.length, roomList);
                    loadRoomTable(searchResult);

                }
            }

        });

    }

    public void loadReservationTable(Reservation reservation) {
        Object[] col_res = {"ID", "Room ", "Entry Date", "Finish Date", "Total Amount", "Guest Number", "Guest Name ", "Guest ID Number", "Mail", "Phone"};
        ArrayList<Object[]> resList = this.reservationManager.getForTable(col_res.length, this.reservationManager.findAll());
        createTable(this.tbl_reservation_model, tbl_reservation, col_res, resList);
    }

    public void LoadReservationTableComponent() {

        tableRowSelect(tbl_reservation);
        this.reservation_menu = new JPopupMenu();

        reservation_menu.add("Güncelle").addActionListener(e -> {
            int selectId = this.getTableSelectedRow(tbl_reservation, 0);
            Reservation selectReservation = this.reservationManager.getById(selectId);
            int selectRoomId = selectReservation.getRoom_id();
            Room selectRoom = this.roomManager.getById(selectRoomId);
            UpdateReservationView reservationGUI = new UpdateReservationView(selectRoom, selectReservation.check_in_date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), selectReservation.check_out_date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), selectReservation.getAdult_count(), selectReservation.getChild_count(), selectReservation);
            reservationGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadReservationTable(null);

                }
            });
        });

        reservation_menu.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectResId = this.getTableSelectedRow(tbl_reservation, 0);
                int selectRoomId = this.reservationManager.getById(selectResId).getRoom_id();
                Room selectedRoom = this.roomManager.getById(selectRoomId);
                selectedRoom.setStock(selectedRoom.getStock() + 1);
                this.roomManager.updateStock(selectedRoom);
                if (this.reservationManager.delete(selectResId)) {
                    Helper.showMsg("done");
                    loadRoomTable(null);
                    loadReservationTable(null);


                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_reservation.setComponentPopupMenu(reservation_menu);


    }
    private void createUIComponents() throws ParseException {
        // TODO: place custom component creation code here
        this.fld_hotel_strt_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_hotel_strt_date.setText("01/06/2024");
        this.fld_hotel_fnsh_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_hotel_fnsh_date.setText("15/06/2024");
    }

}




