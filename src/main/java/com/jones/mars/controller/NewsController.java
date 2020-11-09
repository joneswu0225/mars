package com.jones.mars.controller;

import com.jones.mars.model.News;
import com.jones.mars.model.param.NewsParam;
import com.jones.mars.model.query.NewsQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@Slf4j
@Api(value = "新闻动态", tags = {"新闻动态"})
public class NewsController extends BaseController {

    @Autowired
    private NewsService service;

    @ApiOperation(value = "新闻动态列表", notes = "新闻动态列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam NewsQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "新增新闻动态", notes = "新增新闻动态")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) NewsParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "新闻动态详情", notes = "新闻动态详情")
    @GetMapping("{newsId}")
    public BaseResponse findOne(@PathVariable Integer newsId) {
        return service.findById(newsId);
    }

    @ApiOperation(value = "更新新闻动态", notes = "更新新闻动态")
    @PutMapping("{newsId}")
    public BaseResponse update(
            @PathVariable Integer newsId,
            @RequestBody @ApiParam(required=true) NewsParam param) {
        param.setId(newsId);
        param.setStatus(News.STATUS_EDITING);
        return service.update(param);
    }
    @ApiOperation(value = "发布新闻动态", notes = "发布新闻动态")
    @PutMapping("{newsId}/publish")
    public BaseResponse publish(@PathVariable Integer newsId) {
        return service.updateStatus(newsId, News.STATUS_PUBLISHED);
    }
    @ApiOperation(value = "下架新闻动态", notes = "下架新闻动态")
    @PutMapping("{newsId}/downshelf")
    public BaseResponse downshelf(@PathVariable Integer newsId) {
        return service.updateStatus(newsId, News.STATUS_DOWNSHELF);
    }

    @ApiOperation(value = "置顶新闻动态", notes = "置顶新闻动态")
    @PutMapping("{newsId}/settop")
    public BaseResponse setTop(@PathVariable Integer newsId) {
        return service.updateTopFlg(newsId, News.TOP_SET);
    }

    @ApiOperation(value = "取消置顶新闻动态", notes = "取消置顶新闻动态")
    @PutMapping("{newsId}/canceltop")
    public BaseResponse cancelTop(@PathVariable Integer newsId) {
        return service.updateTopFlg(newsId, News.TOP_CANCEL);
    }

    // TODO 增加后台注解
    @ApiOperation(value = "删除新闻动态", notes = "后台调用")
    @DeleteMapping("{newsId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer newsId) {
        return service.delete(newsId);
    }


}
