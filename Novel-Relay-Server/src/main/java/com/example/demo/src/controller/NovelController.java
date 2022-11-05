package com.example.demo.src.controller;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.request.PatchKeywordReq;
import com.example.demo.src.dto.response.*;
import com.example.demo.src.dto.request.PostRelayReq;
import com.example.demo.src.dto.request.PostSignUpReq;
import com.example.demo.src.dto.request.PostNovelReq;
import com.example.demo.src.entity.RELAY;
import com.example.demo.src.service.KeywordService;
import com.example.demo.src.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/novel")
public class NovelController {

    private final NovelService novelService;
    private final KeywordService keywordService;

    @Autowired
    public NovelController(NovelService novelService, KeywordService keywordService) {
        this.novelService = novelService;
        this.keywordService = keywordService;
    }

    @GetMapping("/{novel_id}")
    public BaseResponse<List<GetNovelIdRes>> getRelayGroup(@PathVariable Long novel_id) throws BaseException {
        List<GetNovelIdRes> relayList = novelService.getRelayGroup(novel_id);
        return new BaseResponse<>(relayList);

    }
    @PostMapping("/{category}/create")
    @ResponseBody
    public BaseResponse<Long> createNovel(@PathVariable String category, @RequestBody PostNovelReq postNovelReq) throws BaseException {

        System.out.println(postNovelReq.getKeywords().toString());
        Long novel_id = novelService.createNovel(category,postNovelReq);

        return new BaseResponse<>(novel_id);
    }

    @GetMapping("part/{category}")
    public BaseResponse<List<GetCategoryRes>> getCateNovelGroup(@PathVariable String category) throws BaseException {
        List<GetCategoryRes> cateNovelList = novelService.getCateNovelGroup(category);
        return new BaseResponse<>(cateNovelList);
    }


    @PostMapping("/{novel_id}/relay")
    @ResponseBody
    public BaseResponse<PostRelayRes> signup(@RequestBody PostRelayReq postRelayReq) throws BaseException {
        PostRelayRes postRelayRes = novelService.postRelay(postRelayReq);

        return new BaseResponse<>(postRelayRes);
    }

    @PatchMapping("/{novel_id}/keyword")
    @ResponseBody
    public BaseResponse<PatchKeywordRes> addKeyword(@PathVariable Long novel_id, @RequestBody PatchKeywordReq patchKeywordReq) throws BaseException {
        PatchKeywordRes patchKeywordRes = keywordService.addKeyword(novel_id, patchKeywordReq);

        return new BaseResponse<>(patchKeywordRes);
    }

    @GetMapping("/all")
    public BaseResponse<List<GetAllRes>> getAllGroup() throws BaseException {
        List<GetAllRes> allgroup =  novelService.getAllGroup();
        return new BaseResponse<>(allgroup);
    }
}
