package com.example.models;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "personal_data_users", schema = "public")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonBinaryType.class)
})
public class PersonalDataUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "personal_data_users_id_seq")
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_user")
    private Integer userId;
    @Column(name = "address")
    private String address;
    @Column(name = "serie_buletin")
    private String serieBuletin;
    @Column(name = "numar_buletin")
    private String numarBuletin;
    @Column(name = "cnp")
    private String cnp;
    @Column(name = "telefon")
    private String phone;
    @Column(name = "judet")
    private String judet;
    @Column(name = "localitate")
    private String localitate;
    @Column(name = "data_nastere")
    private String dataNastere;

    public PersonalDataUsers(Integer userId, String address, String serieBuletin, String numarBuletin, String cnp, String phone, String judet, String localitate, String dataNastere, String key) {
        this.userId = userId;
        this.address = address;
        this.serieBuletin = serieBuletin;
        this.numarBuletin = numarBuletin;
        this.cnp = cnp;
        this.phone = phone;
        this.judet = judet;
        this.localitate = localitate;
        this.dataNastere = dataNastere;
        this.key = key;
    }

    @Column(name = "key")
    private String key;


}
