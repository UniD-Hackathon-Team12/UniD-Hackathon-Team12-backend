package com.example.demo.src.service;

import com.example.demo.config.BaseException;
import com.example.demo.src.dto.request.GetNovelIdReq;
import com.example.demo.src.dto.response.GetCategoryRes;
import com.example.demo.src.dto.response.GetNovelIdRes;
import com.example.demo.src.entity.NOVEL;
import com.example.demo.src.entity.RELAY;
import com.example.demo.src.repository.NovelRepository;
import com.example.demo.src.repository.RelayRepository;
import com.example.demo.utils.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class NovelService {

    private NovelRepository novelRepository;
    private RelayRepository relayRepository;

    @Autowired
    public NovelService(NovelRepository novelRepository, RelayRepository relayRepository) {

        this.novelRepository = novelRepository;
        this.relayRepository = relayRepository;
    }

    public List<GetNovelIdRes> getRelayGroup(Long novel_id) throws BaseException {

        List<RELAY> relayGroup = relayRepository.findByNovelIdInGroup(novel_id);

        if (relayGroup.isEmpty()) {
//            throw new BaseException();
        }
        List<GetNovelIdRes> getNovelIdResList = new ArrayList<>();

        for (RELAY relay: relayGroup) {

            GetNovelIdRes getNovelIdRes = GetNovelIdRes.builder()
                    .relay_id(relay.getRelay_id())
                    .novel_id(relay.getNovel().getNovel_id())
                    .r_content(relay.getR_content())
                    .user_id(relay.getUser().getUser_id())
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

            GetCategoryRes getCategoryRes = GetCategoryRes.builder()
                    .novel_id(novel.getNovel_id())
                    .category(novel.getCategory())
                    .max_num(novel.getMax_num())
                    .n_content(novel.getN_content())
                    .like_count(novel.getLike_count())
                    .relay_count(novel.getRelay_count())
                    .user_id(novel.getUser().getUser_id())
                    .active(novel.isActive())
                    .build();
            getCategoryResList.add(getCategoryRes);
        }
        return getCategoryResList;
    }

}
