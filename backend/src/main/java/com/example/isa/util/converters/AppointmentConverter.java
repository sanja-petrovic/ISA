package com.example.isa.util.converters;

import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import com.example.isa.dto.AppointmentDto;
import com.example.isa.model.Appointment;
import com.example.isa.model.AppointmentStatus;
import com.example.isa.model.BloodDonor;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.service.interfaces.BloodDonorService;
import com.example.isa.util.qrCode.QrCodeGenerator;
import org.springframework.stereotype.Service;

@Service
public class AppointmentConverter implements Converter<Appointment, AppointmentDto>{

	private final BloodDonorService bloodDonorService;
	private final BloodBankService bankService;
	
 	public AppointmentConverter(BloodDonorService bloodDonorService, BloodBankService bankService) {
 		this.bloodDonorService = bloodDonorService;
 		this.bankService = bankService;
 	}
	 
	@Override
	public AppointmentDto entityToDto(Appointment entity) {
		try {
			return new AppointmentDto(
					entity.getId(),
					entity.getStatus().toString(),
					entity.getDateTime().toString(),
					entity.getDuration(),
					entity.getBloodBank().getId().toString(),
					entity.getBloodBank().getTitle(),
					entity.getBloodDonor() == null ? null : entity.getBloodDonor().getPersonalId(),
					entity.getReport(),
					ImageConverter.convertToBase64(QrCodeGenerator.generateQRCodeForAppointment(entity), "png")
					);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Appointment dtoToEntity(AppointmentDto dto) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getDefault());
		BloodDonor donor = dto.getBloodDonorId() == null ? null : bloodDonorService.getByPersonalId(dto.getBloodDonorId());
		if(dto.getId() == null) {
			try {
				return new Appointment(
						AppointmentStatus.valueOf(dto.getStatus()),
						formatter.parse(dto.getDateTime()),
						dto.getDuration(),
						bankService.getById(UUID.fromString(dto.getBloodBankId())),
						donor,
						dto.getReport()
						);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				return new Appointment(
						AppointmentStatus.valueOf(dto.getStatus()),
						formatter.parse(dto.getDateTime()),
						dto.getDuration(),
						bankService.getById(UUID.fromString(dto.getBloodBankId())),
						donor,
						dto.getReport()
						);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public List<AppointmentDto> listToDtoList(List<Appointment> list){
		List<AppointmentDto> retVal = new ArrayList<>();
		for(Appointment appointment : list) {
			retVal.add(this.entityToDto(appointment));
		}
		return retVal;
	}

}
