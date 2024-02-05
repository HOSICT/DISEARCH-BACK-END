package com.example.disearch.controller;


import com.example.disearch.entity.Tag;
import com.example.disearch.entity.Post;
import com.example.disearch.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/main")
public class BoardController {

    private final PostService postService;

    @Autowired
    public BoardController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/board")
    public ResponseEntity<Map<String, Object>> getPosts(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "category", required = false) String category) {

        if (tag != null && category != null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "400",
                    "msg", "Tag and category cannot be searched together."
            ));
        }

        List<Post> posts = postService.getPosts(tag, category);
        List<Map<String, Object>> postList = posts.stream().map(post -> Map.of(
                "id", post.getId(),
                "serverId", post.getServerId(),
                "serverName", post.getServerName(),
                "category", post.getCategory(),
                "tag", post.getTags().stream().map(Tag::getName).collect(Collectors.toList()),
                "content", post.getContent(),
                "createdAt", post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
        )).collect(Collectors.toList());

        Map<String, Object> response = Map.of(
                "status", "200",
                "msg", "ok",
                "data", Map.of("list", postList)
        );

        return ResponseEntity.ok(response);
    }
}
