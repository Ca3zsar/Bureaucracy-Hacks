package com.example.resources;

import com.example.models.Comment;
import com.example.repositories.BureaucraticProcessRepository;
import com.example.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CalculateStatistics {
    CommentRepository commentRepository;
    BureaucraticProcessRepository bureaucraticProcessRepository;

    public String calculateStatistics(String processName) {
        if (!bureaucraticProcessRepository.findByName(processName).isPresent())
            throw new IllegalStateException("Process not found.");
        List<Boolean> q1Answers1 = new ArrayList<>();
        List<Integer> q2Answers1 = new ArrayList<>();
        List<String> q3Answers1 = new ArrayList<>();
        List<Integer> q4Answers1 = new ArrayList<>();
        List<Boolean> q1Answers2 = new ArrayList<>();
        List<Integer> q2Answers2 = new ArrayList<>();
        List<String> q3Answers2 = new ArrayList<>();
        List<Integer> q4Answers2 = new ArrayList<>();
        List<Boolean> q1Answers3 = new ArrayList<>();
        List<Integer> q2Answers3 = new ArrayList<>();
        List<String> q3Answers3 = new ArrayList<>();
        List<Integer> q4Answers3 = new ArrayList<>();
        List<Boolean> q1Answers4 = new ArrayList<>();
        List<Integer> q2Answers4 = new ArrayList<>();
        List<String> q3Answers4 = new ArrayList<>();
        List<Integer> q4Answers4 = new ArrayList<>();
        List<Boolean> q1Answers5 = new ArrayList<>();
        List<Integer> q2Answers5 = new ArrayList<>();
        List<String> q3Answers5 = new ArrayList<>();
        List<Integer> q4Answers5 = new ArrayList<>();
        Integer luni = 0, marti = 0, miercuri = 0, joi = 0, vineri = 0;
        for (Comment comment : commentRepository.findAll(processName)) {
            if (comment.getDay().equalsIgnoreCase("Luni")) {
                q1Answers1.add(comment.getQ1());
                q2Answers1.add(comment.getQ2());
                q3Answers1.add(comment.getQ3());
                q4Answers1.add(comment.getQ4());
                luni++;
            } else if (comment.getDay().equalsIgnoreCase("marti")) {
                q1Answers2.add(comment.getQ1());
                q2Answers2.add(comment.getQ2());
                q3Answers2.add(comment.getQ3());
                q4Answers2.add(comment.getQ4());
                marti++;
            } else if (comment.getDay().equalsIgnoreCase("miercuri")) {
                q1Answers3.add(comment.getQ1());
                q2Answers3.add(comment.getQ2());
                q3Answers3.add(comment.getQ3());
                q4Answers3.add(comment.getQ4());
                miercuri++;
            } else if (comment.getDay().equalsIgnoreCase("joi")) {
                q1Answers4.add(comment.getQ1());
                q2Answers4.add(comment.getQ2());
                q3Answers4.add(comment.getQ3());
                q4Answers4.add(comment.getQ4());
                joi++;
            } else if (comment.getDay().equalsIgnoreCase("vineri")) {
                q1Answers5.add(comment.getQ1());
                q2Answers5.add(comment.getQ2());
                q3Answers5.add(comment.getQ3());
                q4Answers5.add(comment.getQ4());
                vineri++;
            }

        }
        String q11, q12, q13, q14, q15;
        String q21, q22, q23, q24, q25;
        String q31, q32, q33, q34, q35;
        String q41, q42, q43, q44, q45;
        int trueNb1 = 0, trueNb5 = 0, trueNb4 = 0, trueNb3 = 0, trueNb2 = 0;
        for (Boolean q1Answer : q1Answers1) {
            if (q1Answer)
                trueNb1++;
        }
        for (Boolean q1Answer : q1Answers2) {
            if (q1Answer)
                trueNb2++;
        }
        for (Boolean q1Answer : q1Answers3) {
            if (q1Answer)
                trueNb3++;
        }
        for (Boolean q1Answer : q1Answers4) {
            if (q1Answer)
                trueNb4++;
        }
        for (Boolean q1Answer : q1Answers5) {
            if (q1Answer)
                trueNb5++;
        }

        q11 = String.format("%.02f", q1Answers1.size() != 0 ? (double) trueNb1 * 100 / q1Answers1.size() : (double) 0) + "%";
        q12 = String.format("%.02f", q1Answers2.size() != 0 ? (double) trueNb2 * 100 / q1Answers2.size() : (double) 0) + "%";
        q13 = String.format("%.02f", q1Answers3.size() != 0 ? (double) trueNb3 * 100 / q1Answers3.size() : (double) 0) + "%";
        q14 = String.format("%.02f", q1Answers4.size() != 0 ? (double) trueNb4 * 100 / q1Answers4.size() : (double) 0) + "%";
        q15 = String.format("%.02f", q1Answers5.size() != 0 ? (double) trueNb5 * 100 / q1Answers5.size() : (double) 0) + "%";
        int sumQ21 = 0, sumQ22 = 0, sumQ23 = 0, sumQ24 = 0, sumQ25 = 0;
        for (Integer q2Answer : q2Answers1) {
            sumQ21 += q2Answer;
        }
        for (Integer q2Answer : q2Answers2) {
            sumQ22 += q2Answer;
        }
        for (Integer q2Answer : q2Answers3) {
            sumQ23 += q2Answer;
        }
        for (Integer q2Answer : q2Answers4) {
            sumQ24 += q2Answer;
        }
        for (Integer q2Answer : q2Answers5) {
            sumQ25 += q2Answer;
        }

        q21 = String.format("%.02f", q2Answers1.size() != 0 ? (double) sumQ21 / q2Answers1.size() : (double) 0);
        q22 = String.format("%.02f", q2Answers2.size() != 0 ? (double) sumQ22 / q2Answers2.size() : (double) 0);
        q23 = String.format("%.02f", q2Answers3.size() != 0 ? (double) sumQ23 / q2Answers3.size() : (double) 0);
        q24 = String.format("%.02f", q2Answers4.size() != 0 ? (double) sumQ24 / q2Answers4.size() : (double) 0);
        q25 = String.format("%.02f", q2Answers5.size() != 0 ? (double) sumQ25 / q2Answers5.size() : (double) 0);


        int interval11 = 0, interval21 = 0, interval31 = 0, interval41 = 0, interval51 = 0;
        int interval12 = 0, interval22 = 0, interval32 = 0, interval42 = 0, interval52 = 0;
        int interval13 = 0, interval23 = 0, interval33 = 0, interval43 = 0, interval53 = 0;
        int interval14 = 0, interval24 = 0, interval34 = 0, interval44 = 0, interval54 = 0;
        int interval15 = 0, interval25 = 0, interval35 = 0, interval45 = 0, interval55 = 0;
        for (String q3Answer : q3Answers1) {
            if (Objects.equals("0-30", q3Answer))
                interval11++;
            else if (Objects.equals("30-45", q3Answer))
                interval21++;
            else if (Objects.equals("45-60", q3Answer))
                interval31++;
            else if (Objects.equals("60-90", q3Answer))
                interval41++;
            else if (Objects.equals("90+", q3Answer))
                interval51++;
        }
        for (String q3Answer : q3Answers2) {
            if (Objects.equals("0-30", q3Answer))
                interval12++;
            else if (Objects.equals("30-45", q3Answer))
                interval22++;
            else if (Objects.equals("45-60", q3Answer))
                interval32++;
            else if (Objects.equals("60-90", q3Answer))
                interval42++;
            else if (Objects.equals("90+", q3Answer))
                interval52++;
        }
        for (String q3Answer : q3Answers3) {
            if (Objects.equals("0-30", q3Answer))
                interval13++;
            else if (Objects.equals("30-45", q3Answer))
                interval23++;
            else if (Objects.equals("45-60", q3Answer))
                interval33++;
            else if (Objects.equals("60-90", q3Answer))
                interval43++;
            else if (Objects.equals("90+", q3Answer))
                interval53++;
        }
        for (String q3Answer : q3Answers4) {
            if (Objects.equals("0-30", q3Answer))
                interval14++;
            else if (Objects.equals("30-45", q3Answer))
                interval24++;
            else if (Objects.equals("45-60", q3Answer))
                interval34++;
            else if (Objects.equals("60-90", q3Answer))
                interval44++;
            else if (Objects.equals("90+", q3Answer))
                interval54++;
        }
        for (String q3Answer : q3Answers5) {
            if (Objects.equals("0-30", q3Answer))
                interval15++;
            else if (Objects.equals("30-45", q3Answer))
                interval25++;
            else if (Objects.equals("45-60", q3Answer))
                interval35++;
            else if (Objects.equals("60-90", q3Answer))
                interval45++;
            else if (Objects.equals("90+", q3Answer))
                interval55++;
        }
        q31 = "0-30: " + String.format("%.02f", q3Answers1.size() != 0 ? (double) interval11 * 100 / q3Answers1.size() : (double) 0) + "%, " +
                "30-45: " + String.format("%.02f", q3Answers2.size() != 0 ? (double) interval21 * 100 / q3Answers1.size() : (double) 0) + "%, " +
                "45-60: " + String.format("%.02f", q3Answers3.size() != 0 ? (double) interval31 * 100 / q3Answers1.size() : (double) 0) + "%, " +
                "60-90: " + String.format("%.02f", q3Answers4.size() != 0 ? (double) interval41 * 100 / q3Answers1.size() : (double) 0) + "%, " +
                "90+: " + String.format("%.02f", q3Answers5.size() != 0 ? (double) interval51 * 100 / q3Answers1.size() : (double) 0) + "%";

        if (q1Answers2.size() != 0) {
            q32 = "0-30: " + String.format("%.02f", (double) interval12 * 100 / q3Answers2.size()) + "%, " +
                    "30-45: " + String.format("%.02f", (double) interval22 * 100 / q3Answers2.size()) + "%, " +
                    "45-60: " + String.format("%.02f", (double) interval32 * 100 / q3Answers2.size()) + "%, " +
                    "60-90: " + String.format("%.02f", (double) interval42 * 100 / q3Answers2.size()) + "%, " +
                    "90+: " + String.format("%.02f", (double) interval52 * 100 / q3Answers2.size()) + "%";
        } else {
            q32 = "0-30: " + String.format("%.02f", (double) 0) + "%, " +
                    "30-45: " + String.format("%.02f", (double) 0) + "%, " +
                    "45-60: " + String.format("%.02f", (double) 0) + "%, " +
                    "60-90: " + String.format("%.02f", (double) 0) + "%, " +
                    "90+: " + String.format("%.02f", (double) 0) + "%";
        }
        q33 = "0-30: " + String.format("%.02f", q3Answers3.size() != 0 ? (double) interval13 * 100 / q3Answers3.size() : (double) 0) + "%, " +
                "30-45: " + String.format("%.02f", q3Answers3.size() != 0 ? (double) interval23 * 100 / q3Answers3.size() : (double) 0) + "%, " +
                "45-60: " + String.format("%.02f", q3Answers3.size() != 0 ? (double) interval33 * 100 / q3Answers3.size() : (double) 0) + "%, " +
                "60-90: " + String.format("%.02f", q3Answers3.size() != 0 ? (double) interval43 * 100 / q3Answers3.size() : (double) 0) + "%, " +
                "90+: " + String.format("%.02f", q3Answers3.size() != 0 ? (double) interval53 * 100 / q3Answers3.size() : (double) 0) + "%";
        q34 = "0-30: " + String.format("%.02f", q3Answers4.size() != 0 ? (double) interval14 * 100 / q3Answers4.size() : (double) 0) + "%, " +
                "30-45: " + String.format("%.02f", q3Answers4.size() != 0 ? (double) interval24 * 100 / q3Answers4.size() : (double) 0) + "%, " +
                "45-60: " + String.format("%.02f", q3Answers4.size() != 0 ? (double) interval34 * 100 / q3Answers4.size() : (double) 0) + "%, " +
                "60-90: " + String.format("%.02f", q3Answers4.size() != 0 ? (double) interval44 * 100 / q3Answers4.size() : (double) 0) + "%, " +
                "90+: " + String.format("%.02f", q3Answers4.size() != 0 ? (double) interval54 * 100 / q3Answers4.size() : (double) 0) + "%";
        q35 = "0-30: " + String.format("%.02f", q3Answers5.size() != 0 ? (double) interval15 * 100 / q3Answers5.size() : (double) 0) + "%, " +
                "30-45: " + String.format("%.02f", q3Answers5.size() != 0 ? (double) interval25 * 100 / q3Answers5.size() : (double) 0) + "%, " +
                "45-60: " + String.format("%.02f", q3Answers5.size() != 0 ? (double) interval35 * 100 / q3Answers5.size() : (double) 0) + "%, " +
                "60-90: " + String.format("%.02f", q3Answers5.size() != 0 ? (double) interval45 * 100 / q3Answers5.size() : (double) 0) + "%, " +
                "90+: " + String.format("%.02f", q3Answers5.size() != 0 ? (double) interval55 * 100 / q3Answers5.size() : (double) 0) + "%";
        int sumQ41 = 0, sumQ42 = 0, sumQ43 = 0, sumQ44 = 0, sumQ45 = 0;
        for (Integer q4Answer : q4Answers1) {
            sumQ41 += q4Answer;
        }
        for (Integer q4Answer : q4Answers2) {
            sumQ42 += q4Answer;
        }
        for (Integer q4Answer : q4Answers3) {
            sumQ43 += q4Answer;
        }
        for (Integer q4Answer : q4Answers4) {
            sumQ44 += q4Answer;
        }
        for (Integer q4Answer : q4Answers5) {
            sumQ45 += q4Answer;
        }

        if (q1Answers1.size() != 0) {
            q41 = String.format("%.02f", (double) sumQ41 / q4Answers1.size());
        } else {
            q41 = String.format("%.02f", (double) 0);
        }
        if (q1Answers2.size() != 0) {
            q42 = String.format("%.02f", (double) sumQ42 / q4Answers2.size());
        } else {
            q42 = String.format("%.02f", (double) 0);
        }
        if (q1Answers3.size() != 0) {
            q43 = String.format("%.02f", (double) sumQ43 / q4Answers3.size());
        } else {
            q43 = String.format("%.02f", (double) 0);
        }
        if (q1Answers4.size() != 0) {
            q44 = String.format("%.02f", (double) sumQ44 / q4Answers4.size());
        } else {
            q44 = String.format("%.02f", (double) 0);
        }
        if (q1Answers5.size() != 0) {
            q45 = String.format("%.02f", (double) sumQ45 / q4Answers5.size());
        } else {
            q45 = String.format("%.02f", (double) 0);
        }
        JSONObject response = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONArray finalResponse = new JSONArray();
        jsonObject.put("q1", q11);
        jsonObject.put("q2", q21);
        jsonObject.put("q3", q31);
        jsonObject.put("q4", q41);
        response.put("day", "Luni");
        response.put("responses", jsonObject);
        finalResponse.add(response);

        jsonObject = new JSONObject();
        response = new JSONObject();
        jsonObject.put("q1", q12);
        jsonObject.put("q2", q22);
        jsonObject.put("q3", q32);
        jsonObject.put("q4", q42);
        response.put("day", "Marti");
        response.put("responses", jsonObject);
        finalResponse.add(response);

        jsonObject = new JSONObject();
        response = new JSONObject();
        jsonObject.put("q1", q13);
        jsonObject.put("q2", q23);
        jsonObject.put("q3", q33);
        jsonObject.put("q4", q43);
        response.put("day", "Miercuri");
        response.put("responses", jsonObject);
        finalResponse.add(response);

        jsonObject = new JSONObject();
        response = new JSONObject();
        jsonObject.put("q1", q14);
        jsonObject.put("q2", q24);
        jsonObject.put("q3", q34);
        jsonObject.put("q4", q44);
        response.put("day", "Joi");
        response.put("responses", jsonObject);
        finalResponse.add(response);

        jsonObject = new JSONObject();
        response = new JSONObject();
        jsonObject.put("q1", q15);
        jsonObject.put("q2", q25);
        jsonObject.put("q3", q35);
        jsonObject.put("q4", q45);
        response.put("day", "Vineri");
        response.put("responses", jsonObject);
        finalResponse.add(response);

        response = new JSONObject();
        response.put("statistics", finalResponse);
        return response.toString();
    }
}
