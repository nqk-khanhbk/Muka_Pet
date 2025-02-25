package com.muka.petcare.controller;

import com.muka.petcare.service.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/redis")
public class RedisController {
    private final BaseRedisService<String, String, String> baseRedisService;

    @PostMapping("/test")
    public String set(){
        baseRedisService.set("xin chao", "duy phung");
        return "ok";
    }
}
