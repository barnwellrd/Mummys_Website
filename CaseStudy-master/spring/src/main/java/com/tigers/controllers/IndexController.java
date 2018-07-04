package com.tigers.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController { 
	
	@RequestMapping("/")
	public String index(HttpSession session) {
		System.out.println(session.getAttribute("currentUser"));
		return "home";
	}
}