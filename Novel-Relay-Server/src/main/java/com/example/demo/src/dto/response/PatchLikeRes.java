package com.example.demo.src.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class PatchLikeRes {
    private Long user_id;
    private boolean like;
    private Long novel_id;
}
