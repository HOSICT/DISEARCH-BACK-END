package com.example.disearch.controller.dto;

import java.util.List;

public class PostRequest {
    private Long serverId;
    private String serverName;
    private String category;
    private List<String> tag;
    private String content;

    // Getters
    public Long getServerId() {
        return serverId;
    }

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

