package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloContorller {
	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Hello Spring Boot Board"; //developmentOnly 'org.springframework.boot:spring-boot-devtools' 테스르를 위해 생성 (새로고침시 바로 반영을 위함)
	}

}
