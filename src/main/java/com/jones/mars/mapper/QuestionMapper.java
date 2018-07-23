package com.jones.mars.mapper;

import com.jones.mars.model.Query;
import com.jones.mars.model.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {
    List<Question> findList(Query query);
    List<Question> findAll();
    Integer findCount(Query query);
    void insert(Question question);
    void update(Question question);
    Question findOne(Integer questionId);
    void delete(Integer questionId);

}