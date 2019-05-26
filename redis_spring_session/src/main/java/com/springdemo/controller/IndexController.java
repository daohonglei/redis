package com.springdemo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/index")
public class IndexController {

    private final Gson gson = new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();

    @RequestMapping(value = "login")
    public String login(HttpServletRequest request, String name){

        request.getSession().setAttribute("user", gson.toJson(new User(123456L,name)));

        return "login";
    }

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, Model model){

        User user = gson.fromJson(request.getSession().getAttribute("user").toString(), User.class);

        model.addAttribute("user", user);

        return "index";
    }
}
