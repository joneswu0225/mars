package com.jones.mars.controller;

import com.jones.mars.model.GeneralResponse;
import com.jones.mars.model.Hotspot;
import com.jones.mars.model.Query;
import com.jones.mars.model.Question;
import com.jones.mars.service.HsHsService;
import com.jones.mars.service.QuestionService;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by jones on 18-1-16.
 */
@Controller
@RequestMapping( "/question" )
public class QuestionController extends BaseController{
    @Autowired
    private QuestionService questionService;

    @Autowired
    private HsHsService hsHsService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "size", required = false, defaultValue = "10") int size,
                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                     ModelMap map){
        Page<Question> list = questionService.findByPage(getQuery(page, size, new Question()));
        Map<String, String> hotspotMap = hsHsService.findInnerHotspotList(new Query<>()).stream().collect(Collectors.toMap(Hotspot::getCode, Hotspot::getTitle));
        map.put("page", list);
        map.put("hotspotMap", hotspotMap);
        return "question/list";
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Question> findAll(){
        return questionService.findList(new Query<>());
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    GeneralResponse saveComment(Question question) {
        questionService.saveQuestion(question);
        return new GeneralResponse(true, "success");
    }

    @ResponseBody
    @RequestMapping(value = {"/{questionId}"}, method = {RequestMethod.GET})
    Question findOne(@PathVariable("questionId") Integer questionId) {
        return questionService.findOne(questionId);
    }


    @ResponseBody
    @RequestMapping(value = {"/delete"}, method = {RequestMethod.POST})
    GeneralResponse deleteQuestion(@RequestParam("questionId") Integer questionId) {
        questionService.delete(questionId);
        return new GeneralResponse(true, "success");
    }
}