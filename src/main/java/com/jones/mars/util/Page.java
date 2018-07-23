package com.jones.mars.util;

import com.jones.mars.model.Query;

import java.util.List;

public class Page<T> {
    private Integer currentPage = Integer.valueOf(1);
    private Integer number;
    private Integer pageSize = Integer.valueOf(20);
    private Integer totalElements;
    private Integer isMore;
    private Integer totalPages;
    private Integer startIndex;
    private List<T> content;

    public Page() {
    }

    public Page(Integer currentPage, Integer pageSize, Integer totalNum, List<T> content) {
        currentPage = Integer.valueOf(currentPage.intValue() < 1 ? 1 : currentPage.intValue());
        this.currentPage = currentPage;
        this.number = Integer.valueOf(currentPage.intValue() - 1);
        this.pageSize = pageSize;
        this.totalElements = totalNum;
        this.totalPages = Integer.valueOf((this.totalElements.intValue() + this.pageSize.intValue() - 1) / this.pageSize.intValue());
        this.startIndex = Integer.valueOf((this.currentPage.intValue() - 1) * this.pageSize.intValue());
        this.isMore = Integer.valueOf(this.currentPage.intValue() >= this.totalPages.intValue() ? 0 : 1);
        this.content = content;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Page(Query query, Integer totalNum, List<T> content) {
        this(Integer.valueOf(query.getCurrentPage()), Integer.valueOf(query.getPageSize()), totalNum, content);
    }

    public Page(Query query, Integer totalNum) {
        this(Integer.valueOf(query.getCurrentPage()), Integer.valueOf(query.getPageSize()), totalNum, null);
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getIsMore() {
        return this.isMore;
    }

    public void setIsMore(Integer isMore) {
        this.isMore = isMore;
    }

    public Integer getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return this.totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getStartIndex() {
        return this.startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public List<T> getContent() {
        return this.content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}

