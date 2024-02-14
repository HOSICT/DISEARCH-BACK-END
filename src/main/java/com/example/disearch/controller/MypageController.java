package com.example.disearch.controller;

import com.example.disearch.entity.Post;
import com.example.disearch.entity.Tag;
import com.example.disearch.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mypage")
public class MypageController {

    private final PostService postService;

    @Autowired
    public MypageController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/board")
    public ResponseEntity<Map<String, Object>> getMyPosts(@RequestParam(name = "id") String id) {
        List<Post> posts = postService.findPostsByUserId(id);

        List<Map<String, Object>> postList = posts.stream().map(post -> {
            Map<String, Object> postMap = new HashMap<>();
            postMap.put("id", post.getId());
            postMap.put("serverId", post.getServerId());
            postMap.put("serverName", post.getServerName());
            postMap.put("category", post.getCategory());
            postMap.put("iconId", post.getIconId());
            postMap.put("tag", post.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
            postMap.put("content", post.getContent());
            postMap.put("createdAt", post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
            return postMap;
        }).collect(Collectors.toList());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "200");
        responseBody.put("msg", "ok");
        responseBody.put("data", Map.of("list", postList));

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/board")
    public ResponseEntity<?> deletePost(@RequestHeader("id") Long id, @RequestHeader("userId") String userId) {
        try {
            postService.deletePostByIdAndUserId(id, userId);
            return ResponseEntity.ok(Map.of("status", "200", "msg", "ok"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "404", "msg", "Not Found"));
        }
    }
}
