package com.example.Resources;

import com.example.models.Institution;
import com.example.repositories.InstitutionsRepository;
import com.ibm.json.java.JSONArray;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
public class JSONParserFiles {
    InstitutionsRepository institutionsRepository;

    @Transactional
    @Modifying
    public String saveInstitutionList(List<JSONObject> fileContent) {

        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = jsonArray.parse(fileContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        List<Object> list = jsonArray.toList();
        System.out.println(jsonArray);
//        System.out.println(jsonArray.getJSONObject(0).get("name"));
//        Iterator<Object> iterator = jsonArray.iterator();
//        while (iterator.hasNext()) {
//            Institution institution = new Institution();
//            institution.setName((String) iterator.next());
//            institutionsRepository.save(institution);
//        }
        JSONObject jo = new JSONObject();
        jo.put("message","Institutions have been updated");
        return jo.toString();
    }

}
