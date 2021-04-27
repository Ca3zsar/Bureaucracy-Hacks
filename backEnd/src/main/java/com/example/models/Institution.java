package com.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
    private String site;
    @Column(name = "adress")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @JsonIgnore
    @ManyToMany(mappedBy = "institutions")
    List<User> users;

    public Institution(String name, String url, String adress, String phone, String email, double latitude, double longitude) {
        this.name = name;
        this.site = url;
        this.address = adress;
        this.phone = phone;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Institution{" +
                "name='" + name + '\'' +
                '}';
    }
}
