package com.example.disearch.controller;


import com.example.disearch.entity.Tag;
import com.example.disearch.entity.Post;
import com.example.disearch.service.PostService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


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
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 12, Sort.by("createdAt").descending());
        Page<Post> postPage = postService.getPosts(tag, category, pageable);


        List<Map<String, Object>> postList = postPage.getContent().stream().map(post -> Map.of(
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
                "data", Map.of(
                        "list", postList,
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