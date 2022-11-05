package com.example.demo.src.dto.response;


import lombok.*;

@Builder
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetNovelListSearchRes {

    private Long novel_id;
    private String n_content;
}
