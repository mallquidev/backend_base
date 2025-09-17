package com.deone.base.bussoft.core.domain.app.rest.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/admin")
    public String test() {
        return "Test endpoint is working!";
    }
}
