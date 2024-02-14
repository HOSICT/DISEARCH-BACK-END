package com.example.disearch.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class JoinController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${DISCORD_BOT_TOKEN}")
    private String botToken;

    @PostMapping("/join")
    public ResponseEntity<String> joinGuild(@RequestBody Map<String, String> request) {
        String guildId = request.get("serverId");
        String guildsUrl = "https://discord.com/api/guilds/" + guildId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bot " + botToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> guildResponse = restTemplate.exchange(guildsUrl, HttpMethod.GET, entity, String.class);
        JSONObject guildResponseBody = new JSONObject(guildResponse.getBody());
        String systemChannelId = guildResponseBody.getString("system_channel_id");

        String invitesUrl = "https://discord.com/api/channels/" + systemChannelId + "/invites";

        JSONObject requestBody = new JSONObject();
        HttpEntity<String> postEntity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> inviteResponse = restTemplate.exchange(invitesUrl, HttpMethod.POST, postEntity, String.class);


        JSONObject inviteResponseBody = new JSONObject(inviteResponse.getBody());
        if (inviteResponseBody.has("code")) {
            String code = inviteResponseBody.getString("code");
            return ResponseEntity.ok(code);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Code not found in the response");
    }
}