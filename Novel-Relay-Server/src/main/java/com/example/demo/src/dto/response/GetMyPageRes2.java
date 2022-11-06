package com.example.demo.src.dto.response;


import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class GetMyPageRes2 {

    private String nickname;
    private List<List> result;
}
