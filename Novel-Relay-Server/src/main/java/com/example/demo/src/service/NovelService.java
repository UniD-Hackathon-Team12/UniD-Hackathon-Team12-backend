package com.example.demo.src.service;

import com.example.demo.config.BaseException;
import com.example.demo.src.dto.request.GetNovelIdReq;
import com.example.demo.src.dto.request.PostRelayReq;
import com.example.demo.src.dto.response.GetNovelIdRes;
import com.example.demo.src.dto.response.PostRelayRes;
import com.example.demo.src.entity.NOVEL;
import com.example.demo.src.entity.RELAY;
import com.example.demo.src.repository.NovelRepository;
import com.example.demo.src.repository.RelayRepository;
import com.example.demo.src.repository.UserRepository;
import com.example.demo.utils.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    public NovelService(NovelRepository novelRepository, RelayRepository relayRepository, UserRepository userRepository) {

        this.novelRepository = novelRepository;
        this.relayRepository = relayRepository;
        this.userRepository = userRepository;
    }

    public List<RELAY> getRelayGroup(Long novel_id) throws BaseException {

        try{
            ArrayList<RELAY> relayGroup = relayRepository.findByNovelIdInGroup(novel_id);
            return relayGroup;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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

}
