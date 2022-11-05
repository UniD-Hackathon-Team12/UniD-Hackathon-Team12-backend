package com.example.demo.src.controller;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.response.GetNovelIdRes;
import com.example.demo.src.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/novel")
public class NovelController {

    private final NovelService novelService;

    @Autowired
    public NovelController(NovelService novelService) {
        this.novelService = novelService;
    }

//    @GetMapping("/{novel_id}")
//    public BaseResponse<List<GetNovelIdRes>> getReply (@RequestParam(value = "userIdx") Long userIdx) {
////        try {
////            List<GetHomeGroupInfoRes> getGroupHomeData = groupInfoService.loadHomeData(userIdx);    //출석율 부분 수정 필요
////            return new BaseResponse<>(getGroupHomeData);
////        } catch (BaseException e){
////            return new BaseResponse<>(e.getStatus());
////        }
//    }



}
