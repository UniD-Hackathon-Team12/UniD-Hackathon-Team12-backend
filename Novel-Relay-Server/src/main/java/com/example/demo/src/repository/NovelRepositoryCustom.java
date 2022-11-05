package com.example.demo.src.repository;


import com.example.demo.src.dto.request.GetNovelListSearchReq;
import com.example.demo.src.dto.response.GetNovelListSearchRes;

import java.awt.print.Pageable;
import java.util.List;

public interface NovelRepositoryCustom {

    List<GetNovelListSearchRes> searchNovelList(Integer type, GetNovelListSearchReq getNovelListSearchReq);

    List<String> searchKEYWORDS(String keyword);

}
