package com.example.demo.src.service;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.request.GetNovelIdReq;
import com.example.demo.src.dto.response.*;
import com.example.demo.src.dto.request.PostRelayReq;
import com.example.demo.src.dto.response.GetNovelIdRes;
import com.example.demo.src.dto.response.PostRelayRes;
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
import java.util.Optional;

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

    public List<GetAllRes> getAllGroup() {
        List<NOVEL> novelGroup = novelRepository.findByAll();
        List<GetAllRes> getAllList = new ArrayList<>();


        for (NOVEL novel: novelGroup) {


            List<String> keywords =  keywordRepository.findKEYWORDLIST(novel.getNovel_id());

            GetAllRes getAllRes = GetAllRes.builder()
                    .novel_id(novel.getNovel_id())
                    .category(novel.getCategory())
                    .max_num(novel.getMax_num())
                    .n_content(novel.getN_content())
                    .like_count(novel.getLike_count())
                    .relay_count(novel.getRelay_count())
                    .active(novel.isActive())
                    .keywords(keywords)
                    .build();
            getAllList.add(getAllRes);
        }
        return getAllList;


    }

    //특정 소설
    public List<GetNovelIdRes> getRelayGroup(Long novel_id) throws BaseException {

        List<RELAY> relayGroup = relayRepository.findByNovelIdInGroup(novel_id);


        //추가
        List<String> keywords =  keywordRepository.findKEYWORDLIST(novel_id);

        if (relayGroup.isEmpty()) {
//           throw new BaseException();
        }
        List<GetNovelIdRes> getNovelIdResList = new ArrayList<>();

        for (RELAY relay: relayGroup) {

            GetNovelIdRes getNovelIdRes = GetNovelIdRes.builder()
                    .relay_id(relay.getRelay_id())
                    .novel_id(relay.getNovel().getNovel_id())
                    .user_id(relay.getUser().getUser_id())
                    .nickname(relay.getUser().getNickname())
                    .r_content(relay.getR_content())
                    .category(relay.getNovel().getCategory())
                    .max_num(relay.getNovel().getMax_num())
                    .n_content(relay.getNovel().getN_content())
                    .like_count(relay.getNovel().getLike_count())
                    .relay_count(relay.getNovel().getRelay_count())
                    .active(relay.getNovel().isActive())
                    .keywords(keywords)
                    .build();
            getNovelIdResList.add(getNovelIdRes);
        }
        return getNovelIdResList;
    }


    public List<GetCategoryRes> getCateNovelGroup(String category) throws BaseException {

        List<NOVEL> cateNovelGroup = novelRepository.findByCateInGroup(category);

        if (cateNovelGroup.isEmpty()) {
//            throw new BaseException();
        }

        List<GetCategoryRes> getCategoryResList = new ArrayList<>();

        for (NOVEL novel: cateNovelGroup) {

            //추가
            List<String> keywords =  keywordRepository.findKEYWORDLIST(novel.getNovel_id());



            GetCategoryRes getCategoryRes = GetCategoryRes.builder()
                    .novel_id(novel.getNovel_id())
                    .category(novel.getCategory())
                    .max_num(novel.getMax_num())
                    .n_content(novel.getN_content())
                    .like_count(novel.getLike_count())
                    .relay_count(novel.getRelay_count())
                    .user_id(novel.getUser().getUser_id())
                    .active(novel.isActive())
                    .keywords(keywords)
                    .build();
            getCategoryResList.add(getCategoryRes);
        }
        return getCategoryResList;
    }

    public PostRelayRes postRelay(PostRelayReq postRelayReq) throws BaseException {
        try {
            RELAY relay = RELAY.builder()
                    .r_content(postRelayReq.getR_content())
                    .novel(novelRepository.getOne(postRelayReq.getNovel_id()))
                    .user(userRepository.getOne(postRelayReq.getUser_id()))
                    .build();
            relayRepository.save(relay);
            NOVEL novel = novelRepository.getOne(postRelayReq.getNovel_id());
            novel.setRelay_count(novel.getRelay_count() + 1);
            if (novel.getRelay_count() >= novel.getMax_num()) {
                novel.setActive(false);
            }
            novelRepository.save(novel);
            return new PostRelayRes(novel.isActive());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
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
    public List<GetNovelListSearchRes> searchNOVELLIST( Integer type, GetNovelListSearchReq getNovelListSearchReq){
        List<GetNovelListSearchRes> result = novelRepository.searchNovelList(type, getNovelListSearchReq);

        return result;

    }

    public Long PatchLike(Long novel_id, PatchLikeReq patchLikeReq) throws BaseException {

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
            Long cnt = novelRepository.findLikeCnt(novel_id);
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
            Long cnt = novelRepository.findLikeCnt(novel_id);
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
