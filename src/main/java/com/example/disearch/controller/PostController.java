package com.example.disearch.controller;

import com.example.disearch.controller.dto.PostRequest;
import com.example.disearch.controller.dto.PostResponse;
import com.example.disearch.entity.Post;
import com.example.disearch.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/create")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        Post post = postService.createPost(
                postRequest.getServerId(),
                postRequest.getServerName(),
                postRequest.getCategory(),
                postRequest.getTag(),
                postRequest.getContent()
        );
        return ResponseEntity.ok(new PostResponse(post.getServerId()));
    }

}