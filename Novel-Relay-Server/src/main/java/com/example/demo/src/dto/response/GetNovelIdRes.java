package com.example.demo.src.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class GetNovelIdRes {

    private Long relay_id;

    private String r_content;

    private Long novel_id;

    private Long user_id;

}
