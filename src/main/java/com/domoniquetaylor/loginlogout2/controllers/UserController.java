package com.domoniquetaylor.loginlogout2.controllers;

import com.domoniquetaylor.loginlogout2.models.User;
import com.domoniquetaylor.loginlogout2.models.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserDao userDao;

    @RequestMapping (value = "/")
    public String index(Model model, HttpSession session) {

        if (session != null) {
            model.addAttribute("username", session.getAttribute("username"));
        }
        return "index";
    }

    @RequestMapping (value = "/login", method = RequestMethod.GET)
    public String displayLoginForm() {
        return "login";
    }

    @RequestMapping (value = "/login", method = RequestMethod.POST)
    public String processLoginForm(Model model, @ModelAttribute @Valid User user,
                                   @RequestParam String username,
                                   String password, HttpServletRequest request) {

        @SuppressWarnings("unchecked")

        User login_user = userDao.findByUsername(username);

        boolean valid_username = username.length() > 3;
        boolean valid_password = password.length() > 3;
        boolean verified_user = login_user != null;

        if (!valid_username || !valid_password || !verified_user) {
            if (!valid_username) {
                model.addAttribute("missing_username_error", "Username required");
            } else if (!verified_user) {
                model.addAttribute("invalid_username_error", "Username not found. Create an account.");
            } else if (password.equals(login_user.getPassword())) {
                model.addAttribute("incorrect_password_error", "Incorrect password");
            } if (!valid_password) {
                model.addAttribute("missing_password_error", "Password required");
            }
            model.addAttribute("username", username);
            model.addAttribute("title", "Login");
            return "/login";
        } else {

            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            return "redirect:";

            //return "redirect:/my-account/" + login_user.getId();
        }
    }

   @RequestMapping (value = "my-account/{userId}", method = RequestMethod.GET)
   public String displayMySchools(Model model, @PathVariable int userId) {

       User user = userDao.findById(userId);

       model.addAttribute("title", "My Account: " + user.getUsername());
       model.addAttribute("user", user);

       return "my-account";

   }

    @RequestMapping (value = "/create-account", method = RequestMethod.GET)
    public String displayCreateAccountForm(Model model) {

        model.addAttribute(new User());

        return "create-account";
    }

    @RequestMapping (value = "/create-account", method = RequestMethod.POST)
    public String processCreateAccountForm(Model model, @ModelAttribute @Valid User newUser, String username,
                                           String password, String verify_password, Errors errors) {

        boolean valid_username = username.length() > 3 && username.length() < 30;
        boolean valid_password = password.length() > 3 && password.length() < 30;
        boolean valid_verify_password = password.equals(verify_password);

        if (valid_username == false || valid_password == false || valid_verify_password == false) {
            if (!valid_username) {
                model.addAttribute("username_error", "Username must be between 3 and 30 characters");
                model.addAttribute("username", username);
            }
            if (!valid_password) {
                model.addAttribute("password_error", "Password must be between 3 and 30 characters");
            }
            if (!password.equals(verify_password)) {
                model.addAttribute("verify_password_error", "Password and Verify Password do not match");
            }
            return "create-account";
        } else {
            userDao.save(newUser);
            return "redirect:/login";
        }
    }


    @RequestMapping (value = "/logout")
    public String logout(HttpServletRequest request, HttpSession session) {
        session.invalidate();
        return "redirect:";
    }
        }
