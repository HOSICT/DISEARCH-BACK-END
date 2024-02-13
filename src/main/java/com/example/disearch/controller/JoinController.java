package com.example.disearch.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
@RestController
public class JoinController {

    @Value("${DISCORD_BOT_TOKEN}")
    private String botToken;

    @PostMapping("/join")
    public ResponseEntity<String> joinGuild(@RequestBody Map<String, String> request) {
        String serverId = request.get("serverId");
        String url = "https://discord.com/api/guilds/" + serverId + "/templates";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(botToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        JSONObject responseBody = new JSONObject(response.getBody());
        String code = responseBody.getString("code");

        return ResponseEntity.ok(code);
    }
}