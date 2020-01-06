package com.domoniquetaylor.loginlogout2.controllers;

import com.domoniquetaylor.loginlogout2.models.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    UserDao userDao;

    @RequestMapping (value = "/")
    public String index() {

        return "index";
    }

    @RequestMapping (value = "/login", method = RequestMethod.GET)
    public String displayLoginForm() {

        return "login";
    }

    @RequestMapping (value = "/login", method = RequestMethod.POST)
    public String processLoginForm() {

        return "redirect:";
    }

    @RequestMapping (value = "/create-account", method = RequestMethod.GET)
    public String displayCreateAccountForm() {

        return "create-account";
    }

    @RequestMapping (value = "/create-account", method = RequestMethod.POST)
    public String processCreateAccountForm() {

        return "redirect:";
    }

    @RequestMapping (value = "/logout" , method = RequestMethod.GET)
    public String logout() {

        return "redirect:";
    }


}
