 package cn.authDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
 public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        
        return "hello";
    }
    @RequestMapping("/stringtest")
    @ResponseBody
    public String stringTest() {
        
        return "hello";
    }
}
