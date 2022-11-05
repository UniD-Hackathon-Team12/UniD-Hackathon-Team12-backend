package com.example.demo.src.controller;


import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.request.PostSignUpReq;
import com.example.demo.src.dto.response.PostSignUpRes;
import com.example.demo.src.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/signup")
//    @ResponseBody
//    public BaseResponse<PostSignUpRes> signup(@RequestBody PostSignUpReq postSignUpReq){
//
//
//    }


}
