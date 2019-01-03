 package cn.authDemo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
 public class TestController {

    @RequestMapping("/hello")
    public String hello( HttpSession session) {
        UserDetails userDetails=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("xiang", 111);
        System.out.println(session.getAttribute("xiang"));
        System.out.println(userDetails.getAuthorities());
        return "hello";
    }
    @RequestMapping("/stringtest")
    @ResponseBody
    public String stringTest() {
        
        return "hello";
    }
}
