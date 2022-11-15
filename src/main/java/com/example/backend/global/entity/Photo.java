package com.example.backend.global.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Photo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(cascade =  CascadeType.PERSIST,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="footsteps_id")
    private FootstepsPost footstepsPost;

    @Column
    private String sunImg;

    @Column
    private String moldImg;

    @Column
    private String waterImg;

    @Column
    private String drainImg;

    @Column
    private String optionImg;

    @Column
    private String ventilImg;

    @Column
    private String destroyImg;

    @Column
    private String ownerImg;

    @Column
    private String securityImg;
}
