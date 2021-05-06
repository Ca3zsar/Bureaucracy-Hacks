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
@Table(name = "institution_admins", schema = "public")
public class InstitutionAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "institution_admins_id_seq")
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_institution")
    private Integer idInstitution;
    @Column(name = "id_user")
    private Integer idUser;

    public InstitutionAdmin(Integer idInstitution, Integer idUser) {
        this.idInstitution = idInstitution;
        this.idUser = idUser;
    }
}
