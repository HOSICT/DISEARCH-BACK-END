package com.example.disearch.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PostRequest {
    private String serverId;

    private String iconId;
    private String userId;
    private String serverName;
    private String category;
    private List<String> tag;
    private String content;

    public String getServerId() {
        return serverId;
    }

    public String getIconId() { return  iconId; }

    public String getUserId() { return userId; }

    public String getServerName() {
        return serverName;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getTag() {
        return tag;
    }

    public String getContent() {
        return content;
    }


}

