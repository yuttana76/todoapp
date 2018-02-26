package com.example.todoapp.conntroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Greeting {

	@RequestMapping("/greeting")
	public @ResponseBody String greeting() {
		return "Hello";
	}
}
