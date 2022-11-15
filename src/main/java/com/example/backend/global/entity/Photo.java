package com.example.backend.global.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Photo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="footstepsPost_id")
    private FootstepsPost footstepsPost;
    private String postImgUrl;
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


    public Photo(String photoImgUrl, FootstepsPost post){
        this.postImgUrl = photoImgUrl;
        this.footstepsPost = post;

    }
}
