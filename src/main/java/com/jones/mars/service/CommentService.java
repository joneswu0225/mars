package com.jones.mars.service;

import com.jones.mars.model.Comment;
import com.jones.mars.model.Query;
import com.jones.mars.model.Thumbup;
import com.jones.mars.repository.CommentRepository;
import com.jones.mars.repository.HotspotRepository;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private HotspotRepository hotspotRepository;

    public Page<Comment> findByPage(Query<Comment> query) {
        return this.commentRepository.findByPage(query);
    }

    public void saveComment(Comment comment) {
        this.hotspotRepository.save(comment.getHotspot());
        this.commentRepository.save(comment);
    }

    public void saveThumbup(Thumbup thumbup) {
    }
}

