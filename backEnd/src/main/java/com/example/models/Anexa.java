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
@Table(name = "anexe", schema = "public")
public class Anexa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "anexe_id_seq")
    private Integer id;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "file_link")
    private String fileLink;
}
