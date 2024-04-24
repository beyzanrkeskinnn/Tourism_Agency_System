package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setTheme(){   //Add Nimbus theme
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if("Nimbus".equals(info.getName())){
                try{
                    UIManager.setLookAndFeel(info.getClassName());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            }

        }
    }
    public static boolean confirm(String string) {
        optionPaneTR();
        String message;
        if (string.equals("sure")) {
            message = "Bu işlemi yapmak istediğine emin misin ?";
        } else {
            message = string;
        }
        return JOptionPane.showConfirmDialog(null,message,"Please confirm !", JOptionPane.YES_NO_OPTION) == 0;
    }
    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }
    public static boolean isFieldListEmpty(JTextField[] fieldList){
        for(JTextField field : fieldList){
            if(isFieldEmpty(field)){
                return true;
            }
        }
        return false;
    }
    public static void showMsg(String str){ //messages reporting
        String msg;
        String title;
        switch(str){
            case "fill" -> {
                msg= "Lütfen tüm alanları doldururunuz !";
                title="HATA";
            }
            case "done"->{
                msg="İşlem Başarılı";
                title="SONUÇ";
            }
            case "notFound"->{
                msg="Kullanıcı bulunamadı";
                title="Bulunamadı !";
            }
            case "error"->{
                msg="Hatalı İşlem Yaptınız !";
                title="Hata ! !";
            }

            default -> {
                msg=str;
                title="Mesaj";
            }

        }
        JOptionPane.showMessageDialog(null,msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    //makes the screen appear in the middle
    public static int getLocationPoint(String type, Dimension size){
        switch (type){
            case "x":
                return  (Toolkit.getDefaultToolkit().getScreenSize().width-size.width)/2;
            case "y":
                return  (Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;
            default:
                return 0;
        }
    }
    public static void optionPaneTR() { //buttonların türkçe hallerini yazdır
        UIManager.put("OptionPane.okButtonText" , "Tamam");
        UIManager.put("OptionPane.yesButtonText" , "Evet");
        UIManager.put("OptionPane.noButtonText" , "Hayır");
    }


}
