package com.example.demo.src.dto.request;


import lombok.*;
import org.jetbrains.annotations.NotNull;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GetRankReq {

    private Long rank_id;
    private  String keyword;
    private Integer count;

}
