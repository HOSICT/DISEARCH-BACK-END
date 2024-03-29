package com.example.disearch.service;

import com.example.disearch.controller.dto.PostRequest;
import com.example.disearch.entity.Post;
import com.example.disearch.entity.Tag;
import com.example.disearch.repository.PostRepository;
import com.example.disearch.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    @Transactional
    public Post createAndReplacePost(PostRequest postRequest) {
        postRepository.findByServerId(postRequest.getServerId())
                .ifPresent(post ->{
                    Set<Tag> tags = post.getTags();
                    for (Tag tag : tags) {
                        tag.setCount(tag.getCount() - 1);
                        if (tag.getCount() == 0) {
                            tagRepository.delete(tag);
                        } else {
                            tagRepository.save(tag);
                        }
                    }
                    postRepository.delete(post);
                });

        Post newPost = new Post();
        newPost.setServerId(postRequest.getServerId());
        newPost.setServerName(postRequest.getServerName());
        newPost.setIconId(postRequest.getIconId());
        newPost.setUserId(postRequest.getUserId());
        newPost.setCategory(postRequest.getCategory());
        newPost.setContent(postRequest.getContent());

        Set<Tag> tags = new HashSet<>();
        for (String tagName : postRequest.getTag()) {
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
        newPost.setTags(tags);
        return postRepository.save(newPost);
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

    public List<Post> findPostsByUserId(String userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public void deletePostByIdAndUserId(Long id, String userId) {
        Optional<Post> post = postRepository.findByIdAndUserId(id, userId);
        if (post.isPresent()) {
            Post postToDelete = post.get();
            Set<Tag> tags = postToDelete.getTags();
            for (Tag tag : tags) {
                tagRepository.decrementCountByName(tag.getName());
            }
            for (Tag tag : tags) {
                Optional<Tag> tagWithZeroCount = tagRepository.findByName(tag.getName())
                        .filter(t -> t.getCount() == 0);
                tagWithZeroCount.ifPresent(tagRepository::delete);
            }
            postRepository.delete(postToDelete);
        } else {
            throw new RuntimeException("Post not found with id " + id + " and userId " + userId);
        }
    }
    @Transactional
    public void deleteTags(){
        tagRepository.deleteTagsWithZeroCount();
    }
}
