package com.example.demo.src.controller;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.request.PostSignUpReq;
import com.example.demo.src.dto.response.GetRankRes;
import com.example.demo.src.dto.response.PostSignUpRes;
import com.example.demo.src.repository.KeywordRankRepository;
import com.example.demo.src.service.KeywordRankService;
import com.example.demo.src.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keyword")
public class KeywordController {

    private final KeywordRankService keywordRankService;

    @Autowired
    public KeywordController(KeywordRankService keywordRankService) {
        this. keywordRankService = keywordRankService;
    }

    @GetMapping("/rank")
    @ResponseBody
    public BaseResponse<List<GetRankRes>> getTopKeywords() throws BaseException {
        List<GetRankRes> getRankRes = keywordRankService.getTopKeywords();

        return new BaseResponse<>(getRankRes);
    }


}
