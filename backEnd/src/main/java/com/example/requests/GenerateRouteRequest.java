package com.example.requests;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class GenerateRouteRequest {

    private final List<String> avoidVignette;
    private final Double currentLatitude;
    private final Double currentLongitude;
    private final List<Integer> institutions;

}
