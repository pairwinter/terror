package com.pairwinter.assembly.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pairwinter.assembly.model.login.Login;

@Controller
public class LoginController {
	@RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request,HttpServletResponse response,Login loginInfo){
        String username = loginInfo.getName();
        ModelAndView mv = new ModelAndView("/index/index");
        mv.addObject("command1", "123");
        return mv;
    }
}
