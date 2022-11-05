package com.example.demo.src.dto.request;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PatchKeywordReq {
    @NotNull
    private List<String> keywords;
}
