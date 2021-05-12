package com.example.controllers;

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

@RestController
@RequestMapping(path = "/generateroute", method = {RequestMethod.POST, RequestMethod.GET}/*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE*/)
@AllArgsConstructor
public class RouteGeneratorController {
    private RouteGenerator routeGenerator;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public String generateRoute(@RequestBody String request){
        JsonObject object = new JsonParser().parse(request).getAsJsonObject();;

        //getting values from json
        double latitude = object.get("latitude").getAsDouble();
        double longitude = object.get("longitude").getAsDouble();
        int institutionId = object.get("institution").getAsInt();
        String necessary = fromJsonArrayToString((JsonArray) object.get("necessary"));


        return routeGenerator.generateRoute(latitude, longitude, institutionId, necessary);
    }



    private String fromJsonArrayToString(JsonArray jsonArray) {
        String output = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement element =  jsonArray.get(i);
            output = output + element.toString();
        }
        return output;
    }
}



