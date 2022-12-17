package com.example.isa.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class DateConvertor {

	public static LocalDateTime convert (Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	public static Date convert (LocalDateTime dateTime) {
		return java.sql.Timestamp.valueOf(dateTime);
	}
}
