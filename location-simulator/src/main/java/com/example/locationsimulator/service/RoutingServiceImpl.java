package com.example.locationsimulator.service;

import com.example.locationsimulator.model.Location;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
public class RoutingServiceImpl implements RoutingService {
    @Override
    public List<Location> getRoute(Location start, Location end) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = generateRequestUrl(start, end);
        String response = restTemplate.getForObject(requestUrl, String.class);
        return parseResponse(response);
    }

    private String generateRequestUrl(Location start, Location end) {
        StringBuilder uri = new StringBuilder("https://routing.openstreetmap.de/routed-car/route/v1/driving/");
        uri.append(start.getLongitude());
        uri.append(",");
        uri.append(start.getLatitude());
        uri.append(";");
        uri.append(end.getLongitude());
        uri.append(",");
        uri.append(end.getLatitude());
        uri.append("?geometries=geojson&overview=false&alternatives=true&steps=true");

        return uri.toString();
    }

    private List<Location> parseResponse(String response) {
        List<Location> allCoordinates = new ArrayList<>();
        JsonArray steps = new JsonParser()
                .parse(response)
                .getAsJsonObject()
                .getAsJsonArray("routes").get(0).getAsJsonObject()
                .getAsJsonArray("legs").get(0).getAsJsonObject()
                .getAsJsonArray("steps");
        for(JsonElement step : steps) {
            JsonArray coordinateArray = step.getAsJsonObject().getAsJsonObject("geometry").getAsJsonArray("coordinates");
            for(JsonElement element : coordinateArray) {
                JsonArray coordinates = element.getAsJsonArray();
                Location location = new Location(coordinates.get(1).getAsDouble(), coordinates.get(0).getAsDouble());
                allCoordinates.add(location);
            }
        }

        return allCoordinates;
    }
}
