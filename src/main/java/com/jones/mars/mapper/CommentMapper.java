package com.jones.mars.mapper;

import com.jones.mars.model.Comment;
import com.jones.mars.model.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    List<Comment> findList(Query paramQuery);

    Integer findCount(Query paramQuery);

    void insert(Comment paramComment);

    void update(Comment paramComment);
}
