package com.example.isa.util.converters;

import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Service
public class DateConverter {

	public static LocalDateTime convert(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	public static Date convert(LocalDateTime dateTime) {
		return java.sql.Timestamp.valueOf(dateTime);
	}

	public static Date convert(String dateTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getDefault());
		try {
			return formatter.parse(dateTime);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static LocalDateTime convertToLocal(String dateTime) throws ParseException {
		return convert(convert(dateTime));
	}
}
