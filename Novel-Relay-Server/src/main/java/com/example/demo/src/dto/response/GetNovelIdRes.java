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

    private List<RELAY> reply;

}
