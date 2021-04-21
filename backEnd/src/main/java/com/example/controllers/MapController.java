package com.example.controllers;
import com.example.services.MapService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
public class MapController {
    @Autowired
    private MapService mapService;
    @Value("${tomtom.apikey}")
    private String tomTomApiKey;

    @GetMapping("/")
    public String homePage(Model model) {
        Double latitude = 27.587914;
        Double longitude = 47.151726;
//        System.out.println(submitToTomtom("{\n" +
//                "    \"avoidVignette\": [\n" +
//                "      \"AUS\",\"CHE\"\n" +
//                "    ]\n" +
//                "  }"));
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        model.addAttribute("apikey", tomTomApiKey);
        model.addAttribute("currentLocation", currentLocation());
        model.addAttribute("destination", destiationLocation());
        return "home";
    }
    @GetMapping(path = "/generateRoute")
    private Map submitToTomtom(@RequestBody String requestBody) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URIBuilder builder = null;
        try {
            builder = new URIBuilder("https://api.tomtom.com/routing/1/calculateRoute/27.587923%2C47.151736%3A27.5571511%2C47.1489325/json");
            List<NameValuePair> params = new ArrayList<>(2);
            params.add(new BasicNameValuePair("routeType", "shortest"));
            params.add(new BasicNameValuePair("travelMode", "car"));
            params.add(new BasicNameValuePair("key", "uaEXwXJW6AwV8fbWMMYw9qCH9yqpzGBh"));

            builder.setParameters(params);
            System.out.println(builder.toString());
            HttpPost httppost = new HttpPost(builder.build());
            httppost.setHeader("Content-Type","application/json");
            httppost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            httppost.setEntity(new ByteArrayEntity(requestBody.getBytes("utf-8")));
            HttpResponse response = httpclient.execute(httppost);
            ObjectMapper mapper = new ObjectMapper();
            Map resp = mapper.readValue(response.getEntity().getContent(), HashMap.class);

            return resp;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Location currentLocation() {
        return new Location(27.587923, 47.151736, "Locatia curenta");
    }

    private Location destiationLocation() {
        return new Location(27.5571511, 47.1489325, "Institutie");
    }

    private static class Location {
        private final double longitude;
        private final double latitude;
        private final double[] lnglat;
        private final String description;

        public Location(double longitude, double latitude, String description) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.description = description;
            this.lnglat = new double[]{longitude, latitude};
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public double[] getLnglat() {
            return lnglat;
        }

        public String getDescription() {
            return description;
        }
    }

}
