package com.example.demo.src.dto.response;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostSigninRes {
    private Long user_id;
    private String jwt;
}
