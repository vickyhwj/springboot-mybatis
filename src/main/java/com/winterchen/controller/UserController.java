package com.winterchen.controller;

import com.github.pagehelper.PageHelper;
import com.winterchen.model.UserDomain;
import com.winterchen.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/add")
    public int addUser(UserDomain user){
         userService.addUser(user);
         return  1;
    }

    @ResponseBody
    @GetMapping("/all")
    public Object findAllUser(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize,
            String userName){
        return userService.findAllUser(pageNum,pageSize,userName);
    }

    @Resource(name = "secondJdbcTemplate")
    JdbcTemplate secondJdbcTemplate;

    @GetMapping("/testSec")
    public String testSec(){
        System.out.print(secondJdbcTemplate);
        return "Sdasda";
    }
}
