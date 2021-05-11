package com.example.services;


import com.example.models.Institution;
import com.example.repositories.InstitutionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RouteGenerator {
    private InstitutionsRepository institutionsRepository;

    public static boolean hasCopy(String content) {
        String[] tokens = new String[]{"copie", "copii", "xerox", "duplicat"};

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


    public Optional<Institution> getInstByName(String name) {
        return institutionsRepository.findByName(name);
    }

    public Optional<Institution> getInstById(int id) {
        return institutionsRepository.findById(id);
    }


}
