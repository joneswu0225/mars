package com.jones.mars.service;

import com.jones.mars.model.Query;
import com.jones.mars.model.Thumbup;
import com.jones.mars.repository.ThumbupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThumbupService {

    @Autowired
    private ThumbupRepository thumbupRepository;

    public void saveThumbup(Thumbup thumbup) {
        this.thumbupRepository.save(thumbup);
    }

    public void deleteThumbup(Thumbup thumbup) {
        this.thumbupRepository.delete(thumbup);
    }

    public Integer findCount(Query<Thumbup> query) {
        return this.thumbupRepository.findCount(query);
    }
}

