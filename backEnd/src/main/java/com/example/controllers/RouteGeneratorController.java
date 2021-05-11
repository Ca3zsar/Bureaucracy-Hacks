package com.example.controllers;

import com.example.models.Institution;
import com.example.services.RouteGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
//import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping(path = "/generateroute", method = {RequestMethod.POST, RequestMethod.GET}/*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE*/)
@AllArgsConstructor
public class RouteGeneratorController {
    private RouteGenerator routeGenerator;
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public String generateRoute(@RequestBody String request){
//        InstitutionsRepository repository;

        JsonObject object = new JsonParser().parse(request).getAsJsonObject();;

        double latitude = object.get("latitude").getAsDouble();
        double longitude = object.get("longitude").getAsDouble();

        int institutionId = object.get("institution").getAsInt();

        JsonArray jsonArray = (JsonArray) object.get("necessary");

        String necessary = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement element =  jsonArray.get(i);
            necessary += element.toString();
        }

        List<String> titleXeroxList = new ArrayList<>();
        titleXeroxList.add("Lex Copy Center");
        titleXeroxList.add("Nicolina Copy Center");
        titleXeroxList.add("ASL COPY CENTER");

        List<Institution> xeroxList = new ArrayList<>();
        for ( String title : titleXeroxList) {
            Optional<Institution> institution = routeGenerator.getInstByName(title);
            if (institution.isPresent()) {
                xeroxList.add(institution.get());
            }
        }

        Institution theNearest = RouteGenerator.getNearestLocation(latitude, longitude, xeroxList);

        Optional<Institution> institution = routeGenerator.getInstById(institutionId);




        return getUrlResult();
    }

    public String getUrlResult(){
        final String uri = "https://api.tomtom.com/routing/1/calculateRoute/52.50931,13.42936:52.50274,13.43872/json?key=hiof03pLAbXPAybwmlz24906Jp4J644A";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }
}


