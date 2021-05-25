package com.example.services;


import com.example.models.Institution;
import com.example.models.Office;
import com.example.repositories.InstitutionsRepository;
import com.example.repositories.OfficeRepository;
import com.example.utils.Pair;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.corba.se.impl.encoding.CDROutputObject;

//import jdk.internal.util.xml.impl.Pair;
//import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RouteGenerator {

    private InstitutionsRepository institutionsRepository;
    private OfficeRepository officeRepository;

    public static boolean hasCopies(String content) {
        String[] tokens = new String[]{"copie", "copii", "xerox", "duplicat"};
        for (String token : tokens) {
            if (content.contains(token)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPost(String content) {
        String[] tokens = new String[]{"timbru", "timbre", "posta", "postal"};
        for (String token : tokens) {
            if (content.contains(token)) {
                return true;
            }
        }
        return false;
    }


    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }

    public static Institution getNearestLocation(double lat, double lng, List<Institution> institutionList) {
        double minimumDistance = 100_000;
        Institution output = null;
        for (Institution inst : institutionList) {
            double distance = getDistance(lat,lng, inst.getLatitude(), inst.getLongitude()) ;
            if ( distance < minimumDistance ) {
                minimumDistance = distance;
                output = inst;
            }
        }
        return output;
    }
    public static Office getNearestLocationToOffices(double lat, double lng, List<Office> institutionList) {
        double minimumDistance = 100_000;
        Office output = null;
        for (Office office : institutionList) {
            double distance = getDistance(lat,lng, office.getLatitude(), office.getLongitude()) ;
            if ( distance < minimumDistance ) {
                minimumDistance = distance;
                output = office;
            }
        }
        return output;
    }



    public String getUrlResult(Pair<Double, Double> firstCoordinates, Pair<Double, Double> secondCoordinates){
        final String uri = "https://api.tomtom.com/routing/1/calculateRoute/" + firstCoordinates.getValue()  + "," + firstCoordinates.getKey() + ":" + secondCoordinates.getValue() + "," +  secondCoordinates.getKey() + "/json?key=hiof03pLAbXPAybwmlz24906Jp4J644A";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public String generateRoute(double latitude, double longitude, int institutionId, String necessary) {
        Optional<Institution> mainInstitution = institutionsRepository.findById(institutionId);

        List<Institution> locations = new ArrayList<>();
        if (mainInstitution.isPresent()) {
//            locations.add(mainInstitution.get());

            if (hasCopies(necessary)) {
                List<Institution> copyCenters = institutionsRepository.getCopyCenters();
                locations.addAll(copyCenters);
            }

            if (hasPost(necessary)) {
                List<Institution> copyCenters = institutionsRepository.getPosts();
                locations.addAll(copyCenters);
            }

            Pair <List<Pair<Double, Double>>, List<String>> coordinates = getPath(latitude, longitude, locations);

            List<Office> offices =  new ArrayList<>();
            List<Office> officeList = officeRepository.getOfficeList().stream()
                    .filter(office ->office.getInstitution().equals(mainInstitution.get().getName())).collect(Collectors.toList());
            offices.addAll(officeList);

            if(offices.size() > 0)
            {
               Pair<Double, Double> lastCoordinates = coordinates.getKey().get(coordinates.getKey().size()-1);
               Office lastInstitution = getNearestLocationToOffices(lastCoordinates.getValue(), lastCoordinates.getValue(),offices);
               coordinates.getKey().add(new Pair<>(lastInstitution.getLatitude() , lastInstitution.getLongitude()));
               coordinates.getValue().add(lastInstitution.getName());
            }
            else
            {
                coordinates.getKey().add(new Pair<>(mainInstitution.get().getLatitude(), mainInstitution.get().getLongitude()));
                coordinates.getValue().add(mainInstitution.get().getName());
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", "FeatureCollection");

            JsonArray jsonArray = new JsonArray();
            //adding waypoints
            addPoints(coordinates, jsonArray);

            //adding lines
            addLines(coordinates.getKey(), jsonArray);

            jsonObject.add("features", jsonArray);


            return jsonObject.toString();
        }
        else {
            throw new IllegalStateException(/*"{\"message\" : \"*/"Invalid institution id.");
        }
    }


    private void addLines(List<Pair<Double, Double>> coordinates, JsonArray array) {
        int distance = 0;
        JsonObject output = null ;
        for (int index = 0; index < coordinates.size() - 1; index++) {
            output = new JsonObject();
            Pair<Double, Double> firstCoords = coordinates.get(index);
            Pair<Double, Double> secondCoords = coordinates.get(index + 1);

            String TomTomResponse = getUrlResult(firstCoords, secondCoords);

            JsonObject response =  new JsonParser().parse(TomTomResponse).getAsJsonObject();

            JsonArray routes = response.getAsJsonArray("routes");

            output.addProperty("type", "Feature");

            JsonObject geometry = new JsonObject();
            geometry.addProperty("type", "LineString");

            JsonArray coords = new JsonArray();

            JsonObject route = routes.get(0).getAsJsonObject();

            JsonObject summary = route.get("summary").getAsJsonObject();
            int currentDistance = summary.get("lengthInMeters").getAsInt();
            distance += currentDistance;

            JsonArray legs = route.get("legs").getAsJsonArray();
            JsonObject leg = legs.get(0).getAsJsonObject();

            JsonArray points = leg.get("points").getAsJsonArray();

            /// WARNING latitude logitude
            for (int i = 0; i < points.size(); i++) {
                JsonObject point = points.get(i).getAsJsonObject();
                double longitude = point.get("latitude").getAsDouble();
                double latitude = point.get("longitude").getAsDouble();
                JsonArray coord = new JsonArray();
                coord.add(latitude);
                coord.add(longitude);
                coords.add(coord);
            }

            geometry.add("coordinates", coords);

            output.add("geometry", geometry);

            JsonObject properties = new JsonObject();
            properties.addProperty("prop0", "value0");
            properties.addProperty("prop1", "value1");

            output.add("properties", properties);


            array.add(output);

        }
       output.addProperty("distanceInMeters", distance);
    }

    private void addPoints(Pair< List<Pair<Double, Double>>, List<String>> coordinates, JsonArray array) {

        List<Pair<Double, Double>> points = coordinates.getKey();
        for (Pair<Double, Double> point : points) {

            JsonObject properties = new JsonObject();
            int index = points.indexOf(point);
            if (point.equals(coordinates.getKey().get(coordinates.getKey().size()-1)))
            {
                properties.addProperty("marker-color", "#ff1414");
                properties.addProperty("marker-size", "large");
                properties.addProperty("marker-symbol" ,"");
                index = points.indexOf(point);
                properties.addProperty("name", coordinates.getValue().get(index) );
            }
            else if (index > 0 )
            {
                properties.addProperty("name", coordinates.getValue().get(index) );
            }
            else
            {
                properties.addProperty("name", "Current location" );
            }

            JsonObject mainObject = new JsonObject();
            mainObject.addProperty("type", "Feature");
            mainObject.add("properties", properties);


            JsonObject geometry = new JsonObject();
            geometry.addProperty("type", "Point");

            JsonArray coords = new JsonArray();
            coords.add(point.getKey());
            coords.add(point.getValue());

            geometry.add("coordinates", coords);

            mainObject.add("geometry", geometry);

            array.add(mainObject);
        }
    }

    private Pair< List<Pair<Double, Double>>, List<String> > getPath(double latitude, double longitude, List<Institution> list) {
        List<Pair<Double, Double>> output = new ArrayList<>();
        output.add(new Pair<>(latitude, longitude));
        List<String> names = new ArrayList<>();
        names.add("Current location");

        while (!list.isEmpty()) {
            Institution theNearestInstitution = getNearestLocation(latitude, longitude, list);

            if (!theNearestInstitution.getType().equals("institution")) {
                output.add(new Pair<>(theNearestInstitution.getLatitude(), theNearestInstitution.getLongitude()));
                names.add(theNearestInstitution.getName());
                list = list
                        .stream()
                        .filter(institution -> !institution.getType().equals(theNearestInstitution.getType()))
                        .collect(Collectors.toList());
            } else {
                output.add(new Pair<>(theNearestInstitution.getLatitude(), theNearestInstitution.getLongitude()));
                names.add(theNearestInstitution.getName());
                list.remove(theNearestInstitution);
            }
        }
//        System.out.println(output);

        return new Pair<>(output,names);
    }


}