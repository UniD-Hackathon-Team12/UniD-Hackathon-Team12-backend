package com.example.demo.src.service;

import com.example.demo.config.BaseException;
import com.example.demo.src.dto.response.GetRankRes;
import com.example.demo.src.repository.KeywordRankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class KeywordRankService {

    private KeywordRankRepository keywordRankRepository;

    @Autowired

    public KeywordRankService(KeywordRankRepository keywordRankRepository) {
        this.keywordRankRepository = keywordRankRepository;
    }

    public List<GetRankRes> getTopKeywords() throws BaseException {
        return keywordRankRepository.findTop100ByOrderByCountDesc().stream()
                .map(item -> new GetRankRes(item))
                .collect(Collectors.toList());
    }
}
