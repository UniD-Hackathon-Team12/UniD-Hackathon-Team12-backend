package com.example.demo.src.controller;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.request.PostSignUpReq;
import com.example.demo.src.dto.request.PostSigninReq;
import com.example.demo.src.dto.response.GetNovelIdRes;
import com.example.demo.src.dto.response.PostSignUpRes;
import com.example.demo.src.dto.response.PostSigninRes;
import com.example.demo.src.entity.USER;
import com.example.demo.src.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.FAILED_TO_LOGIN;
import static com.example.demo.config.BaseResponseStatus.USERS_EMPTY_USER_ID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseBody
    public BaseResponse<PostSignUpRes> signup(@RequestBody PostSignUpReq postSignUpReq) throws BaseException {
        PostSignUpRes postSignUpRes = userService.signup(postSignUpReq);


        return new BaseResponse<>(postSignUpRes);

    }

    @PostMapping("/signin")
    @ResponseBody
    public BaseResponse<PostSigninRes> signin(@RequestBody PostSigninReq postSigninReq) throws BaseException{
        PostSigninRes postSigninRes = userService.signin(postSigninReq);
        return new BaseResponse<>(postSigninRes);


    }




    @GetMapping("/mypage")
    public BaseResponse<List<List>> getMyNovelGroup() throws BaseException{

        List<List> myNovelList = userService.getMyNovelGroup();
        return new BaseResponse<>(myNovelList);

    }
}
