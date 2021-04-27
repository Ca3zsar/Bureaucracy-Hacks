package com.example.models;

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
@Table(name = "bureaucratic_processes", schema = "public")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonBinaryType.class)
})
public class BureaucraticProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bureaucratic_processes_id_seq")
    private Integer id;
    @Column(name = "bureaucratic_process")
    private String name;
    @Column(name = "id_institution")
    private Integer institution;
    @Column(name = "useful_information", columnDefinition="json")
    @Type(type = "json")
    private String usefulInformation;

    public BureaucraticProcess(Integer id, String name, Integer institution, String usefulInformation) {
        this.id = id;
        this.name = name;
        this.institution = institution;
        this.usefulInformation = usefulInformation;
    }
}
