package com.example.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "institutions", schema = "public")
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "institutions_id_institution_seq")
    @Column(name = "id_institution")
    private Integer id;
    @Column(name = "institution_name")
    private String name;
    @Column(name = "institution_url")
    private String url;
    @Column(name = "adress")
    private String adress;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;


    public Institution(String name, String url, String adress, String phone, String email, double latitude, double longitude) {
        this.name = name;
        this.url = url;
        this.adress = adress;
        this.phone = phone;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
