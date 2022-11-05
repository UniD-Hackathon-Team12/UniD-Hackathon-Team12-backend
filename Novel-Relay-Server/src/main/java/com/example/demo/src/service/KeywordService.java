package com.example.demo.src.service;


import com.example.demo.config.BaseException;
import com.example.demo.src.dto.request.PatchKeywordReq;
import com.example.demo.src.dto.request.PostNovelReq;
import com.example.demo.src.dto.response.GetRankRes;
import com.example.demo.src.dto.response.PatchKeywordRes;
import com.example.demo.src.entity.KEYWORD;
import com.example.demo.src.entity.KEYWORDRANK;
import com.example.demo.src.entity.NOVEL;
import com.example.demo.src.entity.RELAY;
import com.example.demo.src.repository.KeywordRankRepository;
import com.example.demo.src.repository.KeywordRepository;
import com.example.demo.src.repository.NovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static java.util.Objects.isNull;

@Service
@Transactional
public class KeywordService {

    private KeywordRepository keywordRepository;
    private KeywordRankRepository keywordRankRepository;
    private NovelRepository novelRepository;

    @Autowired

    public KeywordService(NovelRepository novelRepository, KeywordRepository keywordRepository, KeywordRankRepository keywordRankRepository) {
        this.novelRepository = novelRepository;
        this.keywordRankRepository = keywordRankRepository;
        this.keywordRepository = keywordRepository;
    }

    public PatchKeywordRes addKeyword(Long novel_id, PatchKeywordReq patchKeywordReq) throws BaseException{

        List<String> keywords = patchKeywordReq.getKeywords();
        for(String keyword : keywords){
            System.out.println(keyword);
            //이거 나중에 추가하는거
            KEYWORD findedkeyword = keywordRepository.findFreq(keyword, novel_id);
            if(isNull(findedkeyword)){
                KEYWORD newKeyword = KEYWORD.builder()
                        .novel(novelRepository.getOne(novel_id))
                        .keyword(keyword)
                        .freq_cnt(1)
                        .build();

                KEYWORD savedkeyword = keywordRepository.save(newKeyword);
            }
            else{
                findedkeyword.setFreq_cnt(findedkeyword.getFreq_cnt() + 1);
                Integer new_freq = keywordRepository.updateFreq(findedkeyword.getFreq_cnt(), keyword, novel_id);
                if(new_freq == 0){
                    System.out.println("error");
                }
                System.out.println("keyword 빈도 update");

            }

            // rank add
            KEYWORDRANK keywordrank = keywordRankRepository.findByKeyword(keyword);
            if(isNull(keywordrank)){
                KEYWORDRANK newKeywordRank = KEYWORDRANK.builder()
                        .keyword(keyword)
                        .count(1)
                        .build();

                KEYWORDRANK savedkeyword = keywordRankRepository.save(newKeywordRank);
            }
            else{
                keywordrank.setCount(keywordrank.getCount() + 1);
                Integer count = keywordRankRepository.updateCount(keywordrank.getCount(), keyword);
                if(count == 0){
                    System.out.println("error");
                }
                System.out.println("keyword 빈도 update");

            }

        }

        return new PatchKeywordRes(novel_id);
    }
}
