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
@Table(name = "RELAY")
public class RELAY extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long relay_id;

    private String r_content;

    @ManyToOne(fetch = LAZY) // N:1 단방향
    @JoinColumn(name = "novel_id")
    private NOVEL novel;

    @ManyToOne(fetch = LAZY) // N:1 단방향
    @JoinColumn(name = "user_id")
    private USER user;

    @Builder
    public RELAY(String r_content, NOVEL novel, USER user) {
        this.r_content = r_content;
        this.novel = novel;
        this.user = user;
    }

}
