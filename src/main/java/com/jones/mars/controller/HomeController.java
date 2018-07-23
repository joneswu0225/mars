package com.jones.mars.controller;

import com.jones.mars.model.GeneralResponse;
import com.jones.mars.util.RandomString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/"})
public class HomeController extends BaseController {
    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/sample"}, method = {RequestMethod.GET})
    public String sample() {
        return "test/sample";
    }

    @RequestMapping(value = {"/ws"}, method = {RequestMethod.GET})
    public String socket() {
        return "test/socket";
    }

    @ResponseBody
    @RequestMapping(value = {"/getCode"}, method = {RequestMethod.GET})
    GeneralResponse getCode() {
        return new GeneralResponse(true, RandomString.generate(5));
    }
}
