package com.jones.mars.model;

import lombok.Data;

@Data
public class Query<T> {
    private static final int DEFAULT_PAGE_SIZE = 20;
    private Integer startRow;
    private int currentPage = 1;
    private int pageSize = 20;
    private T query;

    public Query(){}

    public Query(int pageNum, int pageSize) {
        pageNum = pageNum < 1 ? 1 : pageNum;
        this.currentPage = pageNum;
        this.pageSize = pageSize;
        this.startRow = Integer.valueOf((pageNum - 1) * pageSize);
    }

}
