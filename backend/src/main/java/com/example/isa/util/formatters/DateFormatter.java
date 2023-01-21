package com.example.isa.util.formatters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    public static String formatDate(Date date) {
        return (new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH)).format(date);
    }
}
