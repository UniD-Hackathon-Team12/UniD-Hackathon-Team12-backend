package com.example.demo.src.dto.response;


import com.example.demo.src.entity.KEYWORD;
import com.example.demo.src.entity.KEYWORDRANK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetRankRes {

    private Long rank_id;
    private String keywords;
    private Integer count;


    @Builder
    public GetRankRes(KEYWORDRANK item) {
        this.rank_id = item.getRank_id();
        this.keywords = item.getKeyword();
        this.count = item.getCount();
    }
}
