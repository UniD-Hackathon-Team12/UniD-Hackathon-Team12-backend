package com.example.demo.src.dto.response;

import com.example.demo.src.entity.USER;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class GetMyPageRes {

    private Long novel_id;

    private String category;

    private Integer max_num;

    private String n_content;

    private Long like_count;

    private Long relay_count;

    private boolean active;

    private Long user_id;

    private List<String> keywords;


}
