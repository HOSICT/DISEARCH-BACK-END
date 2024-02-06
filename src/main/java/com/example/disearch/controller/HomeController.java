package com.example.disearch.controller;

import com.example.disearch.entity.Post;
import com.example.disearch.entity.Tag;
import com.example.disearch.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

@RestController
public class HomeController {

    private final PostService postService;

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getHomePage(
            @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<Post> postPage = postService.getPostsPaged(page, null, null);

        Map<String, Object> response = Map.of(
                "status", 200,
                "msg", "ok",
                "data", Map.of(
                        "list", postPage.getContent().stream().map(post -> Map.of(
                                "id", post.getId(),
                                "serverId", post.getServerId(),
                                "serverName", post.getServerName(),
                                "category", post.getCategory(),
                                "tag", post.getTags().stream().map(Tag::getName).collect(Collectors.toList()),
                                "content", post.getContent(),
                                "createdAt", post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
                        )).collect(Collectors.toList()),
                        "totalElements", postPage.getTotalElements(),
                        "totalPages", postPage.getTotalPages(),
                        "curPage", postPage.getNumber(),
                        "first", postPage.isFirst(),
                        "last", postPage.isLast(),
                        "empty", postPage.isEmpty()
                )
        );

        return ResponseEntity.ok(response);
    }
}