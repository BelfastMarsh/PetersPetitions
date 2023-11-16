package com.example.PetersPetitions;

import org.springframework.web.bind.annotation.RequestMapping;

public class Controller {
    @RequestMapping("/")
    public String hello1() {return "index";}
}
