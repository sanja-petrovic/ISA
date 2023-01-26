package com.example.locationsimulator.service;

import com.example.locationsimulator.model.Location;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RoutingService {
    List<Location> getRoute(Location start, Location end);
}
