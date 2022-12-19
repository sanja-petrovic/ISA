package com.example.isa.util.converters;

import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@Service
public class DateConverter {

	public static LocalDateTime convert (Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	public static Date convert (LocalDateTime dateTime) {
		return java.sql.Timestamp.valueOf(dateTime);
	}
}
