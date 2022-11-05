package com.example.demo.src.service;

import com.example.demo.config.BaseException;
import com.example.demo.src.dto.request.GetNovelIdReq;
import com.example.demo.src.dto.request.PostNovelReq;
import com.example.demo.src.dto.response.GetNovelIdRes;
import com.example.demo.src.entity.KEYWORD;
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

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class NovelService {

    private NovelRepository novelRepository;
    private RelayRepository relayRepository;
    private UserRepository userRepository;
    private KeywordRepository keywordRepository;

    @Autowired
    public NovelService(KeywordRepository keywordRepository, NovelRepository novelRepository, RelayRepository relayRepository, UserRepository userRepository) {
        this.keywordRepository = keywordRepository;
        this.novelRepository = novelRepository;
        this.relayRepository = relayRepository;
        this.userRepository = userRepository;
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

}
