package com.example.demo.src.dto.response;

import com.example.demo.src.entity.KEYWORD;
import com.example.demo.src.entity.KEYWORDRANK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatchKeywordRes {

    private Long novel_id;
}
