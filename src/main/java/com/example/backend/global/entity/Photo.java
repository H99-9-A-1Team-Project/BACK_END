package com.example.backend.global.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="footstepsPost_id")
    private FootstepsPost footstepsPost;
    @Column
    private String postImgUrl;

    public Photo(String photoImgUrl, FootstepsPost post){
        this.postImgUrl = photoImgUrl;
        this.footstepsPost = post;

    }
}
