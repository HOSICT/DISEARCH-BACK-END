package com.example.disearch.service;

import com.example.disearch.entity.Tag;
import com.example.disearch.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> findTagById(Long id) {
        return tagRepository.findById(id);
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    public Tag saveOrUpdateTag(String tagName) {
        Optional<Tag> existingTag = tagRepository.findByName(tagName);
        if (existingTag.isPresent()) {
            Tag tag = existingTag.get();
            tag.setCount(tag.getCount() + 1);
            return tagRepository.save(tag);
        } else {
            Tag newTag = new Tag();
            newTag.setName(tagName);
            return tagRepository.save(newTag);
        }
    }
}