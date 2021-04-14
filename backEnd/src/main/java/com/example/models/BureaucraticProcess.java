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
@Table(name = "bureaucratic_processes", schema = "public")
public class BureaucraticProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bureaucratic_processes_id_seq")
    private Integer id;
    @Column(name = "bureaucratic_process")
    private String name;

    public BureaucraticProcess(String name) {
        this.name = name;
    }
}
