package com.adp.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.adp.rest.bean.HelloWorldBean;

@RestController
public class HelloWorldController {

	@Autowired
	private MessageSource messageSource;

	@GetMapping(path = "/hello-world")
	public String sayHello() {
		return "Hello World 100 times";
	}

	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World Bean 100 times");
	}

	@GetMapping("/path-variable/{id}")
	public HelloWorldBean getByIdHelloWorldBean(@PathVariable("id") Integer id) {
		return new HelloWorldBean("Hello World Bean path variables 100 times " + id);
	}

	@GetMapping("/hello-world-i18n")
	public String sayHelloInternationalized() {
		return messageSource.getMessage("hello.world", new Integer[] { 100 }, "Default message",
				LocaleContextHolder.getLocale());
	}
}
