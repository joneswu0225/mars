package com.jones.mars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/order"})
public class OrderController extends BaseController {
    @RequestMapping(value = {"/cart"}, method = {RequestMethod.GET})
    public String cart() {
        return "order/cart";
    }

    @RequestMapping(value = {"/pay"}, method = {RequestMethod.GET})
    public String pay() {
        return "order/pay";

    }
}
