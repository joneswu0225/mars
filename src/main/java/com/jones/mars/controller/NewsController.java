package com.jones.mars.controller;

import com.jones.mars.model.News;
import com.jones.mars.model.param.NewsParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/news")
@Slf4j
@Api(value = "新闻动态", tags = {"新闻动态"})
public class NewsController extends BaseController {

    @Autowired
    private NewsService service;

    @ApiOperation(value = "新闻动态列表", notes = "新闻动态列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam Query query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "新增新闻动态", notes = "新增新闻动态")
    @PostMapping("")
    public BaseResponse add(@Valid @RequestBody @ApiParam(required=true) NewsParam param) {
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
            @Valid @RequestBody @ApiParam(required=true) NewsParam param) {
        News news = new News(param);
        news.setId(newsId);
        return service.update(news);
    }

    // TODO 增加后台注解
    @ApiOperation(value = "删除新闻动态", notes = "后台调用")
    @DeleteMapping("{newsId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer newsId) {
        return service.delete(newsId);
    }


}
