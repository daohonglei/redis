package com;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {


    @RequestMapping("/login")
    public String login(HttpServletRequest request, String username){

        request.getSession().setAttribute("user", new User(1L, "ldh"));

        return "login";
    }

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, Model model){

        User user = (User) request.getSession().getAttribute("user");

        model.addAttribute("user", user);

        return "index";
    }
}
