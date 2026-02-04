package com.nithingodugu.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @GetMapping("/")
    public String index(){
        return "<h1>Hello World</h1>";
    }

    @GetMapping("/test")
    private String indexw(){
        return "<h1>private World</h1>";
    }
}
