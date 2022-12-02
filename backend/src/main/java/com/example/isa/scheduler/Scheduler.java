package com.example.isa.scheduler;

import com.example.isa.dto.BloodSupplyDto;
import com.example.isa.kafka.Producer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class Scheduler {
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public Scheduler(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }


    //@PostConstruct
    public void sendScheduledBloodSupply(Producer producer, BloodSupplyDto message, Date date) {
        threadPoolTaskScheduler.schedule(new ScheduledMessageSender(producer, message), date);
    }
}
