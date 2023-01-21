package com.example.isa.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {

    public static String formatDate(Date date) {
        return (new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH)).format(date);
    }
}
