package com.example.demo.src.dto.request;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostSigninReq {
    private String nickname;
    private String pw;
}
