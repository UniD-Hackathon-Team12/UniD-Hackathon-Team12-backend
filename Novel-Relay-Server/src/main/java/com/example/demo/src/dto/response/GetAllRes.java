package com.example.demo.src.dto.response;

import com.example.demo.src.entity.KEYWORDRANK;
import com.example.demo.src.entity.NOVEL;
import com.example.demo.src.entity.USER;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class GetAllRes {

    private Long novel_id;

    private String category;

    private Integer max_num;

    private String n_content;

    @Builder.Default
    private Long like_count = 0L;

    @Builder.Default
    private Long relay_count = 0L;

    @Builder.Default
    private boolean active = true;

//    @Builder
//    public GetAllRes(NOVEL item) {
//        this.novel_id = item.getNovel_id();
//        this.max_num = item.getMax_num();
//        this.n_content = item.getN_content();
//        this.like_count = item.getLike_count();
//        this.relay_count = item.getRelay_count();
//        this.active = item.isActive();
//    }

}
