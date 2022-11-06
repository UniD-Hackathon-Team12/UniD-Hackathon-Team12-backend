package com.example.demo.src.dto.request;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PatchLikeReq {

    private Long user_id;
}
