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
@Table(name = "sedii", schema = "public")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonBinaryType.class)
})
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sedii_id_seq")
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "institution")
    private String institution;
    @Column(name = "latitude")
    double latitude;
    @Column(name = "longitude")
    double longitude;
//
//    @JsonIgnore
//    @ManyToMany(mappedBy = "institutions")
//    List<User> users;
//    @Column(name = "programs", columnDefinition="json")
//    @Type(type = "json")
//    private String programs;

    @Override
    public String toString() {
        return "Institution{" +
                "name='" + name + '\'' +
                '}';
    }
}
