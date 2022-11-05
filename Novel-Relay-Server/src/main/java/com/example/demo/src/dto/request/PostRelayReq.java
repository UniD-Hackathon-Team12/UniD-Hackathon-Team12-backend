package com.example.demo.src.dto.request;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostRelayReq {

    @NotNull
    private Long novel_id;

    private String r_content;

    private Long user_id;

}
