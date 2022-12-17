package com.example.isa.util.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.UUID;

import com.example.isa.dto.AppointmentDto;
import com.example.isa.model.Appointment;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.service.interfaces.PatientService;

public class AppointmentConverter implements Converter<Appointment, AppointmentDto>{

	private final PatientService patientService;
	private final BloodBankService bankService;
	
 	public AppointmentConverter(PatientService patientService, BloodBankService bankService) {
 		this.patientService = patientService;
 		this.bankService = bankService;
 	}
	 
	@Override
	public AppointmentDto entityToDto(Appointment entity) {
		return new AppointmentDto(
				entity.getUuid(),
				entity.getDateTime().toString(),
				entity.getDuration(),
				entity.getBloodBank().getId().toString(),
				entity.getPatient().getPersonalId()
				);
	}

	@Override
	public Appointment dtoToEntity(AppointmentDto dto) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getDefault());
		
		try {
			return new Appointment(
					dto.getUuid(),
					formatter.parse(dto.getDateTime()),
					dto.getDuration(),
					bankService.getById(UUID.fromString(dto.getBloodBankId())),
					patientService.getByPersonalId(dto.getPatientId())
					);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<AppointmentDto> listToDtoList(List<Appointment> list){
		List<AppointmentDto> retVal = new ArrayList<AppointmentDto>();
		for(Appointment appointment : list) {
			retVal.add(this.entityToDto(appointment));
		}
		return retVal;
	}

}
