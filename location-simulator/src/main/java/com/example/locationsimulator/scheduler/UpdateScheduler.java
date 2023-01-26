package com.example.locationsimulator.scheduler;

import com.example.locationsimulator.communications.Producer;
import com.example.locationsimulator.model.Location;
import com.example.locationsimulator.model.TrackingRequest;
import com.example.locationsimulator.service.RoutingService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class UpdateScheduler {
    private final RoutingService routingService;

    public UpdateScheduler(RoutingService routingService) {
        this.routingService = routingService;
    }

    public void scheduleUpdate(TrackingRequest trackingRequest, Producer producer) {
        List<Location> coordinates = routingService.getRoute(trackingRequest.getStart(), trackingRequest.getEnd());
        TimerTask timerTask = new UpdateTimerTask(trackingRequest, producer, coordinates);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, trackingRequest.getFrequencyInSeconds() * 1000L);
    }

}
