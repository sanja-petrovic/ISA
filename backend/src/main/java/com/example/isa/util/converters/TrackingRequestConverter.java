package com.example.isa.util.converters;

import com.example.isa.dto.locator.TrackingRequestDto;
import com.example.isa.model.locator.Location;
import com.example.isa.model.locator.TrackingRequest;
import com.example.isa.model.locator.TrackingRequestStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TrackingRequestConverter implements Converter<TrackingRequest, TrackingRequestDto> {
    @Override
    public TrackingRequestDto entityToDto(TrackingRequest trackingRequest) {
        return new TrackingRequestDto(
                trackingRequest.getId(),
                trackingRequest.getFrequencyInSeconds(),
                trackingRequest.getStart().getLatitude(),
                trackingRequest.getStart().getLongitude(),
                trackingRequest.getEnd().getLatitude(),
                trackingRequest.getEnd().getLongitude(),
                trackingRequest.getStatus().toString());
    }

    @Override
    public TrackingRequest dtoToEntity(TrackingRequestDto trackingRequestDto) {
        return new TrackingRequest(
                trackingRequestDto.id(),
                new Date(),
                trackingRequestDto.frequencyInSeconds(),
                new Location(trackingRequestDto.latitudeStart(), trackingRequestDto.longitudeStart()), //Location of blood bank
                new Location(44.797731767760375, 20.458045885543164), //Coordinates of Clinical Centre of Serbia in Belgrade
                TrackingRequestStatus.valueOf(trackingRequestDto.status())
                );
    }
}
