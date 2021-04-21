package com.example.services;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import com.example.repositories.InstitutionsRepository;

import java.util.Arrays;
import java.util.List;


@Service
@AllArgsConstructor
@Setter
public class MapService {
    InstitutionsRepository institutionsRepository;
    public Double getLatitude(String institutionName){
        return institutionsRepository.getLatitude(institutionName);
    }
    public Double getLongitude(String institutionName){
        return institutionsRepository.getLongitude(institutionName);
    }

    public List<Double> center() {
//        Double[] coordinates = new Double[]{getLatitude("ANAF"),getLongitude("ANAF")};
        return Arrays.asList(getLatitude("ANAF"),getLongitude("ANAF"));
    }
}
