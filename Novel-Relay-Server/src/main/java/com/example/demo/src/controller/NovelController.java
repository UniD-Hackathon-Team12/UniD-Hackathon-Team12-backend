package com.example.demo.src.controller;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.response.GetCategoryRes;
import com.example.demo.src.dto.response.GetNovelIdRes;
import com.example.demo.src.entity.RELAY;
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

    @GetMapping("/{novel_id}")
    public BaseResponse<List<GetNovelIdRes>> getRelayGroup(@PathVariable Long novel_id) throws BaseException {
        List<GetNovelIdRes> relayList = novelService.getRelayGroup(novel_id);
        return new BaseResponse<>(relayList);
    }

    @GetMapping("part/{category}")
    public BaseResponse<List<GetCategoryRes>> getCateNovelGroup(@PathVariable String category) throws BaseException {
        List<GetCategoryRes> cateNovelList = novelService.getCateNovelGroup(category);
        return new BaseResponse<>(cateNovelList);
    }


}
