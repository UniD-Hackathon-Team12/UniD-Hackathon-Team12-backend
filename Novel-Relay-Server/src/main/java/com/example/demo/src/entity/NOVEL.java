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
@Table(name = "NOVEL")
public class NOVEL extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long novel_id;

    private String category;

    private Integer max_num;

    private String n_content;

    private Long like_count;

    private Long relay_count;

    @Builder.Default
    private boolean active = true;

    @ManyToOne(fetch = LAZY) // N:1 단방향
    @JoinColumn(name = "user_id")
    private USER user;

}
