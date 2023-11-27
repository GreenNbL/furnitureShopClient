package Checkings;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checkings {
    public static boolean ContainsLetters(JTextField field){
        String pattern = "^[^a-zA-Zа-яА-Я]*$";
        if (field.getText().matches(pattern)) return true;
        else return false;
    }
    public static boolean ContainsNumber(JTextField field){
        String pattern = "^[^0-9]*$";
        if (field.getText().matches(pattern)) return true;
        else return false;
    }
    public static boolean IsPhoneNumberCorrect(JTextField field){
        String pattern = "^\\+\\d{12}$";
        // паттерн для проверки формата +ХХХХХХХХХХХХ
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(field.getText());
        if (matcher.matches()) return true;
        else return false;
    }
    public static boolean IsEmptyField(JTextField field){
        if(field.getText().isEmpty()) return true;
        else return false;
    }
    public static boolean isValidDate(JTextField field) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(field.getText());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
