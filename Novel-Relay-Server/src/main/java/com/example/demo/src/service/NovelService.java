package com.example.demo.src.service;

import com.example.demo.config.BaseException;
import com.example.demo.src.dto.request.GetNovelIdReq;
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

    public List<RELAY> getRelayGroup(Long novel_id) throws BaseException {

        try{
            ArrayList<RELAY> relayGroup = relayRepository.findByNovelIdInGroup(novel_id);
            return relayGroup;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
