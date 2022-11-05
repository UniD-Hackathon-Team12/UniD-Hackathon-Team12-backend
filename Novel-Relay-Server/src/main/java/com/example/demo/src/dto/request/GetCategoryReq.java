package com.example.demo.src.dto.request;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GetCategoryReq {
     private String category;
}
