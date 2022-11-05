package com.example.demo.src.dto.response;

import com.example.demo.src.entity.RELAY;
import lombok.*;

import java.util.List;

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
