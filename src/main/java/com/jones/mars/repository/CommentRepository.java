package com.jones.mars.repository;

import com.jones.mars.mapper.CommentMapper;
import com.jones.mars.model.Comment;
import com.jones.mars.model.Query;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {

    @Autowired
    private CommentMapper commentMapper;

    public Page<Comment> findByPage(Query<Comment> query) {
        Integer count = findCount(query);
        return new Page(query, count, findList(query));
    }

    public List<Comment> findList(Query<Comment> query) {
        return this.commentMapper.findList(query);
    }

    public Integer findCount(Query<Comment> query) {
        return this.commentMapper.findCount(query);
    }

    public void save(Comment comment) {
        if (comment.getCommentId() == null)
            this.commentMapper.insert(comment);
        else
            this.commentMapper.update(comment);
    }
}

