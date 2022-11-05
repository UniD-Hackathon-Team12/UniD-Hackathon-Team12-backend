package com.example.demo.src.controller;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.response.GetCategoryRes;
import com.example.demo.src.dto.request.PostRelayReq;
import com.example.demo.src.dto.request.PostSignUpReq;
import com.example.demo.src.dto.request.PostNovelReq;
import com.example.demo.src.dto.response.GetNovelIdRes;
import com.example.demo.src.dto.response.PostRelayRes;
import com.example.demo.src.dto.response.PostSignUpRes;
import com.example.demo.src.dto.request.GetNovelListSearchReq;
import com.example.demo.src.dto.request.PatchLikeReq;
import com.example.demo.src.dto.request.PostNovelReq;
import com.example.demo.src.dto.response.GetNovelIdRes;
import com.example.demo.src.dto.response.GetNovelListSearchRes;
import com.example.demo.src.entity.RELAY;
import com.example.demo.src.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.INVALID_KEYWORD;

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
    //keyword 검색
    @GetMapping("/keyword/search/{keyword}")
    @ResponseBody
    public BaseResponse<List<String>> searchKEYWORDS(@PathVariable String keyword){
        System.out.println("와?");
//        if(keyword.charAt(0) != '#'){
//            return new BaseResponse<>(INVALID_KEYWORD);
//        }
//        keyword = keyword.substring(1);
//        System.out.println("검색어  : "+ keyword);
        List<String> result = novelService.searchKEYWORDS(keyword);

        return new BaseResponse<>(result);

    }

    //n_content 검색
    @GetMapping("/{type}/search")
    @ResponseBody
    public BaseResponse<List<GetNovelListSearchRes>> searchNOVELLIST(@PathVariable Integer type, @RequestBody GetNovelListSearchReq getNovelListSearchReq){
        System.out.println("start"+getNovelListSearchReq.getR_content());
        List<GetNovelListSearchRes> result = novelService.searchNOVELLIST(type, getNovelListSearchReq);
        return new BaseResponse<>(result);
    }


    @PatchMapping("{novel_id}/like")
    @ResponseBody
    public BaseResponse<Long> PatchLike(@PathVariable("novel_id") Long novel_id, @RequestBody PatchLikeReq patchLikeReq) throws BaseException {

        System.out.println(">>>>>>>>novelid " + novel_id);
        System.out.println(patchLikeReq.getUser_id());
        System.out.println(patchLikeReq.isActive());
        Long result = novelService.PatchLike(novel_id,patchLikeReq);
        if(result != 200L){
            return new BaseResponse<>(DATABASE_ERROR);
        }

        return new BaseResponse<>(result);

    }



    @PostMapping("/{novel_id}/relay")
    @ResponseBody
    public BaseResponse<PostRelayRes> signup(@RequestBody PostRelayReq postRelayReq) throws BaseException {
        PostRelayRes postRelayRes = novelService.postRelay(postRelayReq);

        return new BaseResponse<>(postRelayRes);

    }
}
