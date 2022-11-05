package com.example.demo.src.repository;


import com.example.demo.src.dto.request.GetNovelListSearchReq;
import com.example.demo.src.dto.response.GetNovelListSearchRes;
import com.example.demo.src.entity.KEYWORD;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.src.entity.QKEYWORD.kEYWORD;
import static com.example.demo.src.entity.QRELAY.rELAY;
import static com.example.demo.src.entity.QNOVEL.nOVEL;



@Repository
@RequiredArgsConstructor
public class NovelRepositoryImpl implements NovelRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    public List<GetNovelListSearchRes> searchNovelList(Integer type, GetNovelListSearchReq getNovelListSearchReq){

        List<Long> ids = new ArrayList<>();
        if(type == 1){ //keyword 검색
            ids = queryFactory
                    .select(kEYWORD.novel.novel_id)
                    .from(kEYWORD)
                    //.where(kEYWORD.keyword.eq(getNovelListSearchReq.getKeyword()))
                    .where(keywordEq(getNovelListSearchReq.getKeyword()))
                    .fetch();
        }
        else if(type == 2){ //r_content 검색
            System.out.println("찾고자하는 내용 : "+getNovelListSearchReq.getR_content());
            ids = queryFactory
                    .select(rELAY.novel.novel_id)
                    .from(rELAY)
                    .where(rELAY.r_content.contains(getNovelListSearchReq.getR_content()))
                    .fetch();
        }
        else{
            System.out.println("뭔가 잘못 됨");
        }
        System.out.println("찾은거 : "+ids.toString());

        List<GetNovelListSearchRes> result =
                queryFactory
                        .select(Projections.fields(GetNovelListSearchRes.class,
                                nOVEL.novel_id.as("novel_id"),
                                nOVEL.n_content.as("n_content")))
                        .from(nOVEL)
                        .where(nOVEL.novel_id.in(ids))
                        .fetch();

        return result;

    }

    public BooleanExpression keywordEq(String keyword){

        return keyword != null ? kEYWORD.keyword.eq(keyword) : null;



    }

    public List<String> searchKEYWORDS(String keyword){
        List<String> keywords =
                queryFactory.select(kEYWORD.keyword).distinct()
                        .from(kEYWORD)
                        .where(kEYWORD.keyword.contains(keyword))
                        .fetch();

        return keywords;

    }



}
