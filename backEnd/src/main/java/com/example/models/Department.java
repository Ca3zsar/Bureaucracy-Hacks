package com.example.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonSerialize
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Table(name = "departments", schema = "public")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonBinaryType.class)
})
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "departments_id_department_seq")
    @Column(name="id_department")
    private Integer id;
    @Column(name = "department_name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "program", columnDefinition="json")
    @Type(type = "json")
    private String program;
    @OneToOne
    @JoinColumn(nullable = false, name = "id_institution")
    private Institution institution;
    public Department(Institution institution, String name, String phone, String email, String program) {
        this.institution = institution;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.program = program;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", institution=" + institution +
                '}';
    }
}
