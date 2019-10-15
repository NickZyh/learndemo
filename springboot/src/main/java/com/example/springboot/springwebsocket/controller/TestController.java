package com.example.springboot.springwebsocket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author nick.zhou
 * @Date 2019/7/30 10:30
 * @Description <p>TODO</p>
 **/
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("test1")
    public String test(@RequestParam String name, @RequestParam int sex) {
        System.out.println("test execute");
        return name + sex;
    }

    @GetMapping("test2")
    public String test1(@RequestParam String name, @RequestParam int sex) {
        System.out.println("test execute");
        return name + sex;
    }

    @GetMapping("test3")
    public String test2(@RequestParam String name, @RequestParam int sex) {
        System.out.println("test execute");
        return name + sex;
    }
}
