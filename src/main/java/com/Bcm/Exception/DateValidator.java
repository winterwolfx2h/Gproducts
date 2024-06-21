package com.Bcm.Exception;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidator {

    public static boolean isValidDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        dateFormat.setLenient(false);

        try {

            dateFormat.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
