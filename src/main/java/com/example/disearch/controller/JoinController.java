package com.example.disearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JoinController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String DISCORD_API_BASE_URL = "https://discord.com/api/guilds/";

    @PostMapping("/join")
    public ResponseEntity<?> joinGuild(@RequestBody Map<String, String> request) {
        String serverId = request.get("serverId");
        String url = DISCORD_API_BASE_URL + serverId + "/templates";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bot MTE5NzQyOTMwMjQxMzc1NDM5OA.GFeman.iXA3iq82cr-J4F7H4ivxm3hyKEudSc5GmhqYvc");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> response = responseEntity.getBody();

        if (response != null && response.containsKey("code")) {
            String code = (String) response.get("code");
            Map<String, String> result = new HashMap<>();
            result.put("code", code);
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body("Unable to retrieve code from the external API.");
    }
}