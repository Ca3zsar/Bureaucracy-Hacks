package com.example.requests;

import lombok.*;

import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class UpdateProcesses {
    private String institution;
    private String process;
    private Integer id;
    private List<String> departaments;
    private List<String> cases;
    private List<List<String>> forms;
    private List<List<List<String>>> necessary;
    private List<List<String>> generalInfo;
    private List<List<String>> prices;
    private List<String> files;

    public UpdateProcesses(String institution, String process, Integer id, List<String> departaments, List<String> cases, List<List<String>> forms, List<List<List<String>>> necessary, List<List<String>> generalInfo, List<List<String>> prices, List<String> files) {
        this.institution = institution;
        this.process = process;
        this.id = id;
        this.departaments = departaments;
        this.cases = cases;
        this.forms = forms;
        this.necessary = necessary;
        this.generalInfo = generalInfo;
        this.prices = prices;
        this.files = files;
    }
}
