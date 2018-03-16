package com.example.sbp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class testController {

    @RequestMapping("/")
    @ResponseBody
    public String test(){
        return "hello world";
    }

    @RequestMapping("/udload")
    public String udload(){
        return "upload-download/uploadDownload";
    }

}
