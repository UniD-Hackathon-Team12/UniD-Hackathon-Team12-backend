package com.example.demo.src.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KEYWORD")
public class KEYWORD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long keyword_id;

    private String keyword;

    @Builder.Default
    private Integer freq_cnt = 1;


    @ManyToOne(fetch = LAZY) // N:1 단방향
    @JoinColumn(name = "novel_id")
    private NOVEL novel;

}
