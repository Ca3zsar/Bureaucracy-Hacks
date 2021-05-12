package com.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.thymeleaf.expression.Maps;


public class TomTomMap {
    private String name;
    private String surname;

    public TomTomMap(@JsonProperty("name") String name,@JsonProperty("surname") String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
