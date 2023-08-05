package com.example.task_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HtmlSenderController {

    @RequestMapping(value = "/auth/login/", method = RequestMethod.GET)
    public String getLoginHtml(){
        return "test";
    }
}
