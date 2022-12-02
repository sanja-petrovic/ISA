package com.example.isa.service.implementation;

import com.example.isa.dto.BloodRequestDto;
import com.example.isa.dto.BloodRequestResponseDto;
import com.example.isa.dto.BloodSupplyDto;
import com.example.isa.kafka.Producer;
import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodRequest;
import com.example.isa.model.BloodRequestStatus;
import com.example.isa.model.BloodType;
import com.example.isa.repository.BloodRequestRepository;
import com.example.isa.scheduler.ScheduledMessageSender;
import com.example.isa.scheduler.Scheduler;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.service.interfaces.BloodRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class BloodRequestServiceImpl implements BloodRequestService {

    private final BloodBankService bloodBankService;
    private final Producer producer;
    private final BloodRequestRepository bloodRequestRepository;
    private final Scheduler scheduler;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;


    public BloodRequestServiceImpl(BloodBankService bloodBankService, Producer producer, BloodRequestRepository bloodRequestRepository, Scheduler scheduler, ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.bloodBankService = bloodBankService;
        this.producer = producer;
        this.bloodRequestRepository = bloodRequestRepository;
        this.scheduler = scheduler;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    @Override
    public void handleBloodRequest(BloodRequestDto bloodRequestDto) throws JsonProcessingException, ParseException {
        BloodBank bloodBank = bloodBankService.findByTitle((bloodRequestDto.getBloodBank()));

        BloodType bloodType = BloodType.valueOf(bloodRequestDto.getBloodType().split(" ")[0] + "_" + bloodRequestDto.getBloodType().split(" ")[1]);
        System.out.println(bloodType);
        if(bloodBank != null) {
            boolean canSend = bloodBankService.updateBloodSupplies(bloodBank, bloodType, bloodRequestDto.getAmount());
            this.save(bloodRequestDto, bloodType, bloodBank, canSend);
            this.respond(bloodRequestDto.getId(), canSend ? "APPROVED_BY_BANK" : "REJECTED_BY_BANK");
            if(canSend) {
                BloodSupplyDto bloodSupplyDto = new BloodSupplyDto(bloodRequestDto.getId(), bloodRequestDto.getBloodType(), bloodRequestDto.getAmount());
                if(bloodRequestDto.isUrgent()) {
                    producer.send(bloodSupplyDto);
                    this.respond(bloodRequestDto.getId(), "FULFILLED");
                } else {
                    Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(bloodRequestDto.getSendOnDate());
                    scheduler.sendScheduledBloodSupply(producer, bloodSupplyDto, date);
                }
            }
        } else {
            this.respond(bloodRequestDto.getId(), "FAILED");
        }
    }

    public void test() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 2);
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 2);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        System.out.println(date);
        scheduler.sendScheduledBloodSupply(producer, null, date);
    }
    private void respond(UUID requestId, String status) throws JsonProcessingException {
        BloodRequestResponseDto response = BloodRequestResponseDto.builder()
                .requestId(requestId)
                .status(status)
                .build();
        producer.send(response);
    }

    public void save(BloodRequestDto bloodRequestDto, BloodType bloodType, BloodBank bloodBank, boolean canSend) throws ParseException {
        BloodRequest bloodRequest = BloodRequest.builder()
                .id(bloodRequestDto.getId())
                .bloodType(bloodType)
                .amount(bloodRequestDto.getAmount())
                .bloodBank(bloodBank)
                .receivedDate(new Date(System.currentTimeMillis()))
                .sendOnDate(new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(bloodRequestDto.getSendOnDate()))
                .status(canSend ? BloodRequestStatus.APPROVED : BloodRequestStatus.REJECTED)
                .build();
        bloodRequestRepository.save(bloodRequest);
    }
}
