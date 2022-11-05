package com.example.demo.src.dto.request;


import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostNovelReq {

    private Long user_id;

    //키워드 리스트
    private List<String> keywords;
    //최대 수
    private Integer max_num;
    //내용
    private String n_content;


}
