package com.example.controllers;

import com.example.requests.CommentRequest;
import com.example.requests.ReviewRequest;
import com.example.services.CommentService;
import com.example.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user", method = {RequestMethod.POST, RequestMethod.GET})
@AllArgsConstructor
public class ClientRequestController {
    private ReviewService reviewService;
    private CommentService commentService;
    @RequestMapping(path = "/contact", method = {RequestMethod.POST, RequestMethod.GET})
//    @PostMapping(path = "contact")
    public String giveReview(@RequestBody ReviewRequest request) {
        return reviewService.giveReview(request);
    }
    @RequestMapping(path = "/{process}/feedbacks", method = {RequestMethod.POST, RequestMethod.GET})
//    @PostMapping(path = "contact")
    public String giveComment(@PathVariable("process") String process, @RequestBody CommentRequest request) {
//        process = request.getProcess();
        System.out.println(process);
        return commentService.giveComment(process, request);
    }
}