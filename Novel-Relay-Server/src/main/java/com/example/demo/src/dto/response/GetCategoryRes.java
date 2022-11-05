package com.example.demo.src.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class GetCategoryRes {
    private Long novel_id;

    private String category;

    private Integer max_num;

    private String n_content;

    private Long like_count;

    private Long relay_count;

    private boolean active;

    private Long user_id;
}
