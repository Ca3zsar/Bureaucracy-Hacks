package com.example.resources;

import com.example.models.Comment;
import com.example.repositories.BureaucraticProcessRepository;
import com.example.repositories.CommentRepository;
import lombok.AllArgsConstructor;
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
        List<Boolean> q1Answers = new ArrayList<>();
        List<Integer> q2Answers = new ArrayList<>();
        List<String> q3Answers = new ArrayList<>();
        List<Integer> q4Answers = new ArrayList<>();
        for (Comment comment : commentRepository.findAll(processName)) {
            q1Answers.add(comment.getQ1());
            q2Answers.add(comment.getQ2());
            q3Answers.add(comment.getQ3());
            q4Answers.add(comment.getQ4());
        }
        String q1;
        String q2;
        String q3;
        String q4;
        int trueNb = 0;
        for (Boolean q1Answer : q1Answers) {
            if (q1Answer)
                trueNb++;
        }
        q1 = String.format("%.02f", (double) trueNb * 100 / q1Answers.size()) + "%";

        int sumQ2 = 0;
        for (Integer q2Answer : q2Answers) {
            sumQ2 += q2Answer;
        }
        q2 = String.format("%.02f", (double) sumQ2 / q2Answers.size());

        int interval1 = 0, interval2 = 0, interval3 = 0, interval4 = 0, interval5 = 0;
        for (String q3Answer : q3Answers) {
            if (Objects.equals("0-30", q3Answer))
                interval1++;
            else if (Objects.equals("30-45", q3Answer))
                interval2++;
            else if (Objects.equals("45-60", q3Answer))
                interval3++;
            else if (Objects.equals("60-90", q3Answer))
                interval4++;
            else if (Objects.equals("90+", q3Answer))
                interval5++;
        }
        q3 = "0-30: " + String.format("%.02f", (double) interval1 * 100 / q3Answers.size()) + "%, " +
                "30-45: " + String.format("%.02f", (double) interval2 * 100 / q3Answers.size()) + "%, " +
                "45-60: " + String.format("%.02f", (double) interval3 * 100 / q3Answers.size()) + "%, " +
                "60-90: " + String.format("%.02f", (double) interval4 * 100 / q3Answers.size()) + "%, " +
                "90+: " + String.format("%.02f", (double) interval5 * 100 / q3Answers.size()) + "%";

        int sumQ4 = 0;
        for (Integer q4Answer : q4Answers) {
            sumQ4 += q4Answer;
        }
        q4 = String.format("%.02f", (double) sumQ4 / q4Answers.size());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("q1", q1);
        jsonObject.put("q2", q2);
        jsonObject.put("q3", q3);
        jsonObject.put("q4", q4);
        return jsonObject.toString();
    }
}
