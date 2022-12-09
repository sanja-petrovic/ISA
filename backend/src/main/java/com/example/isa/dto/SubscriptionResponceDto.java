package com.example.isa.dto;

import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionResponceDto {
	public String SubscriptionId;
	public String messageString;
}
