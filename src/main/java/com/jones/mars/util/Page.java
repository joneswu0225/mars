package com.jones.mars.util;

import com.jones.mars.model.query.Query;
import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private Integer currentPage = Integer.valueOf(1);
    private Integer pageSize = Integer.valueOf(20);
    private Integer total;
    private List<T> content;

    public Page() {
    }

    public Page(Integer currentPage, Integer pageSize, Integer totalNum, List<T> content) {
        currentPage = Integer.valueOf(currentPage.intValue() < 1 ? 1 : currentPage.intValue());
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = totalNum;
        this.content = content;
    }

    public Page(Query query, Integer totalNum, List<T> content) {
        this(Integer.valueOf(query.getPage()), Integer.valueOf(query.getSize()), totalNum, content);
    }

    public Page(Query query, Integer totalNum) {
        this(Integer.valueOf(query.getPage()), Integer.valueOf(query.getSize()), totalNum, null);
    }

}

