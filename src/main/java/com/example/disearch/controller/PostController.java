package com.example.disearch.controller;

import com.example.disearch.controller.dto.PostRequest;
import com.example.disearch.controller.dto.PostResponse;
import com.example.disearch.entity.Post;
import com.example.disearch.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/create")
public class PostController {

    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest) {
        Post post = postService.createAndReplacePost(postRequest);
        postService.deleteTags();


        Map<String, String> data = Collections.singletonMap("serverId", post.getServerId());

        PostResponse postResponse = new PostResponse(200, "ok", data);

        return ResponseEntity.ok(postResponse);
    }

}