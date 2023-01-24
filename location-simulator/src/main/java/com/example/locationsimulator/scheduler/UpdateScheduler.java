package com.example.locationsimulator.scheduler;

import com.example.locationsimulator.communications.Producer;
import com.example.locationsimulator.model.TrackingRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

@Component
public class UpdateScheduler {
    private final ThreadPoolTaskScheduler taskScheduler;

    public UpdateScheduler(ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void scheduleUpdate(TrackingRequest trackingRequest, Producer producer) {
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(trackingRequest.getUpdateFrequency().getValue(), trackingRequest.getUpdateFrequency().getUnit());
        periodicTrigger.setFixedRate(true);
        //trigger koji ide svake sekunde recimo i broji sekunde ali proverava jel vreme da salje update
        taskScheduler.schedule(new UpdateSender(trackingRequest, producer), periodicTrigger);
    }

}
