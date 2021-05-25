package com.example.controllers;

import com.example.models.*;
import com.example.repositories.*;
import com.example.requests.CommentRequest;
import com.example.requests.ReviewRequest;
import com.example.requests.UserSettingsRequest;
import com.example.services.BureaucraticProcessService;
import com.example.services.CommentService;
import com.example.services.ReviewService;
import com.example.services.UserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/user", method = {RequestMethod.POST, RequestMethod.GET})
@AllArgsConstructor
public class ClientRequestController {
    private BureaucraticProcessService bureaucraticProcessService;
    private ReviewService reviewService;
    private CommentService commentService;
    private CommentRepository commentRepository;
    private BureaucraticProcessRepository bureaucraticProcessRepository;
    private InstitutionsRepository institutionsRepository;
    private DepartmentRepository departmentRepository;
    private ReviewRepository reviewRepository;
    private AnexeRepository anexeRepository;
    private UserService userService;

    @RequestMapping(path = "/contact", method = {RequestMethod.POST, RequestMethod.GET})
    public String giveReview(@RequestBody ReviewRequest request) {
        return reviewService.giveReview(request);
    }


    @RequestMapping(path = "/**/feedbacks", method = {RequestMethod.POST, RequestMethod.GET})
    public String giveComment(HttpServletRequest process, @RequestBody CommentRequest request) {
        String pattern = (String)
                process.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String searchTerm = new AntPathMatcher().extractPathWithinPattern(pattern,
                process.getServletPath());
        searchTerm = searchTerm.substring(0,searchTerm.lastIndexOf("/"));
        return commentService.giveComment(searchTerm, request);
    }

    @RequestMapping(path = "/**/viewfeedbacks", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewFeedbacks(HttpServletRequest process) {
        String pattern = (String)
                process.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String searchTerm = new AntPathMatcher().extractPathWithinPattern(pattern,
                process.getServletPath());
        searchTerm = searchTerm.substring(0,searchTerm.lastIndexOf("/"));
        JSONArray jsonArray = new JSONArray();
        JSONObject response = new JSONObject();
        for (Comment comment : commentRepository.findAll(searchTerm)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", comment.getId());
            jsonObject.put("comment", comment.getComment());
            jsonObject.put("username", comment.getUser().getUsername());
            jsonObject.put("show", comment.getShow());
            jsonObject.put("day", comment.getDay());
            jsonArray.put(jsonObject);
        }
        response.put("process", searchTerm);
        response.put("comments", jsonArray);
        return response.toString();
    }
    @RequestMapping(path = "/**/viewInstitutionFeedbacks", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewInstitutionFeedbacks(HttpServletRequest institution) {
        String pattern = (String)
                institution.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String searchTerm = new AntPathMatcher().extractPathWithinPattern(pattern,
                institution.getServletPath());
        searchTerm = searchTerm.substring(0, searchTerm.lastIndexOf("/"));
        JSONArray jsonArray = new JSONArray();
        JSONObject response = new JSONObject();
        for (BureaucraticProcess bureaucraticProcess : bureaucraticProcessRepository.findByInstitutionId(institutionsRepository.findByName(searchTerm).get().getId())) {
            for (Comment comment : commentRepository.findAll(bureaucraticProcess.getName())) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("process", comment.getProcess());
                jsonObject.put("id", comment.getId());
                jsonObject.put("comment", comment.getComment());
                jsonObject.put("username", comment.getUser().getUsername());
                jsonObject.put("show", comment.getShow());
                jsonObject.put("day", comment.getDay());
                jsonArray.put(jsonObject);
            }
        }
        response.put("institution", institutionsRepository.findByName(searchTerm).get().getId());
        response.put("feedbacks", jsonArray);
        return response.toString();
    }

    @RequestMapping(path = "/viewreviews", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewReviews() {
        JSONArray jsonArray = new JSONArray();
        JSONObject response = new JSONObject();
        for (Review review : reviewRepository.getAllReviews()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", review.getIdFeedback());
            jsonObject.put("comment", review.getComment());
            jsonObject.put("rating", review.getRating());
            jsonObject.put("username", review.getUser().getUsername());
            jsonArray.put(jsonObject);
        }
        response.put("reviews", jsonArray);
        return response.toString();
    }

    @RequestMapping(path = "/process/**", method = {RequestMethod.POST, RequestMethod.GET})
    public String getProcessInformation(HttpServletRequest process){
        String pattern = (String)
                process.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String searchTerm = new AntPathMatcher().extractPathWithinPattern(pattern,
                process.getServletPath());

        JSONObject response = new JSONObject();
        BureaucraticProcess bureaucraticProcess = bureaucraticProcessRepository.findByName(searchTerm).get();
        response.put("process", searchTerm);
        response.put("institution", institutionsRepository.findById(bureaucraticProcess.getInstitution()).get().getName());
        response.put("id", bureaucraticProcess.getId());
        JSONObject usefulInformation = new JSONObject(bureaucraticProcess.getUsefulInformation());
        response.put("departaments", usefulInformation.getJSONArray("departaments"));
        response.put("cases", usefulInformation.getJSONArray("cases"));
        response.put("forms", usefulInformation.getJSONArray("forms"));
        response.put("necessary", usefulInformation.getJSONArray("necessary"));
        response.put("generalInfo", usefulInformation.getJSONArray("generalInfo"));
        response.put("prices", usefulInformation.getJSONArray("prices"));
        response.put("files", usefulInformation.getJSONArray("files"));
        return response.toString();
    }

    @RequestMapping(path = "/institution/**", method = {RequestMethod.POST, RequestMethod.GET})
    public String getInstitutionInformation(HttpServletRequest institutionName){
        String pattern = (String)
                institutionName.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String searchTerm = new AntPathMatcher().extractPathWithinPattern(pattern,
                institutionName.getServletPath());
        JSONObject response = new JSONObject();
        Institution institution = institutionsRepository.findByName(searchTerm).get();
        response.put("name", institution.getName());
        response.put("id", institution.getId());
        response.put("latitude", institution.getLatitude());
        response.put("longitude", institution.getLongitude());
        response.put("phone", institution.getPhone());
        response.put("email", institution.getEmail());
        response.put("url", institution.getSite());
        response.put("address", institution.getAddress());
        JSONArray jsonArray = new JSONArray();
        List<Department> departmentList = departmentRepository.getDepartmentsList(searchTerm);
        for (Department department : departmentList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", department.getName());
            jsonObject.put("program", new JSONObject(department.getProgram()));
            jsonArray.put(jsonObject);
        }
        response.put("departments", jsonArray);
        String finalResponse = response.toString().replaceAll("\\\\n", " ");
        finalResponse = finalResponse.replaceAll("\\\\\"", "\"");
        return finalResponse;
    }

    @PostMapping(path = "getfilelink")
    public String getFileLink(@RequestBody String fileName)
    {
        JSONObject jsonObject = new JSONObject(fileName);
        return anexeRepository.getFileLink(jsonObject.getString("fileName"));
    }

    @PostMapping(path = "updatePersonalData")
    public String updateSettings(@RequestBody UserSettingsRequest body){

        return userService.addPersonalData(body);
    }
}