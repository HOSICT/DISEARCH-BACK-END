package com.example.disearch.service;

import com.example.disearch.entity.Post;
import com.example.disearch.entity.Tag;
import com.example.disearch.repository.PostRepository;
import com.example.disearch.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PostService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public Post createPost(String serverId, String serverName, String iconId, String userId, String category, List<String> tagNames, String content) {
        Post post = new Post();
        post.setServerId(serverId);
        post.setServerName(serverName);
        post.setIconId(iconId);
        post.setUserId(userId);
        post.setCategory(category);
        post.setContent(content);

        Set<Tag> tags = new HashSet<>();
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName)
                    .map(existingTag -> {
                        existingTag.setCount(existingTag.getCount() + 1);
                        return existingTag;
                    })
                    .orElseGet(() -> {
                        Tag newTag = new Tag(tagName);
                        newTag.setCount(1);
                        return newTag;
                    });
            tags.add(tag);
            tagRepository.save(tag);
        }
        post.setTags(tags);
        return postRepository.save(post);
    }

    public Page<Post> getPosts(String tag, String category, Pageable pageable) {
        if (tag != null) {
            return postRepository.findAllByTagsName(tag, pageable);
        } else if (category != null) {
            return postRepository.findAllByCategory(category, pageable);
        } else {
            return postRepository.findAll(pageable);
        }
    }

    public Page<Post> getPostsPaged(int page, String tag, String category) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("id").descending());

        if (tag != null) {
            return postRepository.findAllByTagsName(tag, pageable);
        } else if (category != null) {
            return postRepository.findAllByCategory(category, pageable);
        } else {
            return postRepository.findAll(pageable);
        }
    }

}
