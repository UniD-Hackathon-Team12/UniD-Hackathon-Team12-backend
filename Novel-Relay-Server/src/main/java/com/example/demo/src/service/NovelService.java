package com.example.demo.src.service;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.request.GetNovelIdReq;
import com.example.demo.src.dto.request.GetNovelListSearchReq;
import com.example.demo.src.dto.request.PatchLikeReq;
import com.example.demo.src.dto.request.PostNovelReq;
import com.example.demo.src.dto.response.GetNovelIdRes;
import com.example.demo.src.dto.response.GetNovelListSearchRes;
import com.example.demo.src.entity.KEYWORD;
import com.example.demo.src.entity.LIKEINFO;
import com.example.demo.src.entity.NOVEL;
import com.example.demo.src.entity.RELAY;
import com.example.demo.src.repository.KeywordRepository;
import com.example.demo.src.repository.NovelRepository;
import com.example.demo.src.repository.RelayRepository;
import com.example.demo.src.repository.UserRepository;
import com.example.demo.utils.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class NovelService {

    private NovelRepository novelRepository;
    private RelayRepository relayRepository;
    private UserRepository userRepository;
    private KeywordRepository keywordRepository;
    private LikeInfoService likeInfoService;


    @Autowired
    public NovelService(NovelRepository novelRepository, RelayRepository relayRepository, UserRepository userRepository, KeywordRepository keywordRepository, LikeInfoService likeInfoService) {
        this.novelRepository = novelRepository;
        this.relayRepository = relayRepository;
        this.userRepository = userRepository;
        this.keywordRepository = keywordRepository;
        this.likeInfoService = likeInfoService;
    }



    public List<RELAY> getRelayGroup(Long novel_id) throws BaseException {

        try {
            ArrayList<RELAY> relayGroup = relayRepository.findByNovelIdInGroup(novel_id);
            return relayGroup;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Long createNovel(String category, PostNovelReq postNovelReq) throws BaseException{
        NOVEL newNovel = NOVEL.builder()
                .category(category)
                .max_num(postNovelReq.getMax_num())
                .n_content(postNovelReq.getN_content())
                .user(userRepository.getOne(postNovelReq.getUser_id()))
                .build();


        NOVEL saved = novelRepository.save(newNovel);

        Long novel_id = saved.getNovel_id();

        List<String> keywords = postNovelReq.getKeywords();
        for(String keyword : keywords){
            System.out.println(keyword);
            //이거 나중에 추가하는거
//            Integer freq = keywordRepository.findFreq(keyword);
//            if(freq == 0){
//                freq = 0;
//                KEYWORD newKeyword = KEYWORD.builder()
//                        .novel(novelRepository.getOne(novel_id))
//                        .keyword(keyword)
//                        .freq_cnt(freq)
//                        .build();
//
//                KEYWORD savedkeyword = keywordRepository.save(newKeyword);
//            }
//            else{
//                freq += 1;
//                Integer new_freq = keywordRepository.updateFreq(freq,keyword);
//                if(new_freq == 0){
//                    System.out.println("error");
//                }
//                System.out.println("keyword 빈도 update");
//
//            }

            KEYWORD newKeyword = KEYWORD.builder()
                    .novel(novelRepository.getOne(novel_id))
                    .keyword(keyword)
                    .build();

            KEYWORD savedkeyword = keywordRepository.save(newKeyword);


        }

        RELAY new_relay = RELAY.builder()
                .r_content(saved.getN_content())
                .novel(novelRepository.getOne(novel_id))
                .user(userRepository.getOne(postNovelReq.getUser_id()))
                .build();

        RELAY savedRelay = relayRepository.save(new_relay);

        if(savedRelay.getNovel().getNovel_id() != saved.getNovel_id()){
            System.out.println("뭔가 잘못 됨");
            throw new BaseException(DATABASE_ERROR);
        }

        return novel_id;



    }

    //키워드검색
    public List<String> searchKEYWORDS(String keyword){
        List<String> result = novelRepository.searchKEYWORDS(keyword);

        System.out.println("확인 : "+ result.toString());
        return result;

    }

    // 내용 검색
    public List<GetNovelListSearchRes> searchNOVELLIST(@PathVariable Integer type, GetNovelListSearchReq getNovelListSearchReq){
        List<GetNovelListSearchRes> result = novelRepository.searchNovelList(type, getNovelListSearchReq);

        return result;

    }

    public Long PatchLike(Long novel_id, PatchLikeReq patchLikeReq) throws BaseException {
        Long cnt = 0L;

        if(patchLikeReq.isActive() == true){ //좋아요 활성화
            //처음이냐 있는지... 그럼 만들어야됨......
            LIKEINFO find =  likeInfoService.findLIKEINFO(patchLikeReq.getUser_id(), novel_id);
            if(find == null){ //만들어서 좋아요 활성화 처리함.
                LIKEINFO new_likeinfo = likeInfoService.createLIKEINFO(patchLikeReq.getUser_id(), novel_id);
                if(new_likeinfo.isLikeinfo_active() != true){ //잘못됨.
                    throw new BaseException(DATABASE_ERROR);
                }
            }
            else{ //좋아요로 변경
                Integer check = likeInfoService.changeActive(patchLikeReq.isActive(), patchLikeReq.getUser_id(), novel_id);
                if(check == 0){//제대로 안바뀜.
                    throw new BaseException(DATABASE_ERROR);
                }
            }

            //좋아요 눌러서 cnt + 1 됨.
            Integer check2 = changeLikeCount(cnt+1L, novel_id);
            if(check2 == 0){//제대로 안바뀜.
                throw new BaseException(DATABASE_ERROR);
            }

        }
        else{
            //좋아요 비활성화
            Integer check = likeInfoService.changeActive(patchLikeReq.isActive(), patchLikeReq.getUser_id(), novel_id);
            if(check == 0){//제대로 안바뀜.
                throw new BaseException(DATABASE_ERROR);
            }

            //좋아요 비활성화 눌러서 cnt - 1 됨.
            Integer check3 = changeLikeCount(cnt-1L, novel_id);
            if(check3 == 0){//제대로 안바뀜.
                throw new BaseException(DATABASE_ERROR);
            }


        }


        return 200L;


    }
    public Integer changeLikeCount(Long like_count, Long novel_id){

        Integer check = novelRepository.updateLikeCnt(like_count, novel_id);
        return check;

    }








    }
