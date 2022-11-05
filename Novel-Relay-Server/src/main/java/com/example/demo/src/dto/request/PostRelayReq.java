package com.example.demo.src.dto.request;

import com.example.demo.src.entity.RELAY;
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

    @NotNull
    private String r_content;

    @NotNull
    private Long user_id;

}
