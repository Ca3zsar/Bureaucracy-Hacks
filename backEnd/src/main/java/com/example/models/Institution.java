package com.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "institutions", schema = "public")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonBinaryType.class)
})
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
    @Column(name = "programs", columnDefinition="json")
    @Type(type = "json")
    private String programs;

    public Institution(String name, String site, String address, String phone, String email, Double latitude, Double longitude, List<User> users, String programs) {
        this.name = name;
        this.site = site;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.users = users;
        this.programs = programs;
    }

    public Institution(String name, String site, String address, String phone, String email, Double latitude, Double longitude, String programs) {
        this.name = name;
        this.site = site;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.programs = programs;
    }

    @Override
    public String toString() {
        return "Institution{" +
                "name='" + name + '\'' +
                '}';
    }
}
