package com.example.demo.src.dto.request;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GetNovelListSearchReq {

    private String keyword;
    private String r_content;

}
