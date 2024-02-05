package com.example.disearch.controller.dto;

public class PostResponse {
    private Long serverId;

    public PostResponse(Long serverId) {
        this.serverId = serverId;
    }


    public Long getServerId() {
        return serverId;
    }
}

