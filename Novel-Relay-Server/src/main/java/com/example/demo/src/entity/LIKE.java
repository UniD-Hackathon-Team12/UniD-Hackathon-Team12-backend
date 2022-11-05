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
@Table(name = "LIKE")
public class LIKE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long like_id;


    @Builder.Default
    private boolean like = true;


    @ManyToOne(fetch = LAZY) // N:1 단방향
    @JoinColumn(name = "novel_id")
    private NOVEL novel;

    @ManyToOne(fetch = LAZY) // N:1 단방향
    @JoinColumn(name = "user_id")
    private USER user;
}
