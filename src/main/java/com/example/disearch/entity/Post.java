package com.example.disearch.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long serverId;
    private String iconId;
    private String serverName;
    private String category;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    public Long getId() {
        return id;
    }

    public Long getServerId() {
        return serverId;
    }

    public String getIconId() {
        return iconId;
    }

    public String getServerName() {
        return serverName;
    }

    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
