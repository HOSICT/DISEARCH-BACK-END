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


        List<Map<String, Object>> postList = postPage.getContent().stream().map(post -> {
            List<String> tagNames = Optional.ofNullable(post.getTags())
                    .orElse(Collections.emptySet())
                    .stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList());

            String createdAt = Optional.ofNullable(post.getCreatedAt())
                    .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")))
                    .orElse(null);

            Map<String, Object> postMap = new LinkedHashMap<>();
            postMap.put("id", post.getId());
            postMap.put("serverId", post.getServerId());
            postMap.put("serverName", post.getServerName());
            postMap.put("iconId", post.getIconId());
            postMap.put("category", post.getCategory());
            postMap.put("tag", tagNames);
            postMap.put("content", post.getContent());
            postMap.put("createdAt", createdAt);
            return postMap;
        }).collect(Collectors.toList());

        Map<String, Object> response = Map.of(
                "status", 200,
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