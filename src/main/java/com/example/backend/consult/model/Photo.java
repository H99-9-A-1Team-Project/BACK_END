package com.example.backend.consult.model;

import com.example.backend.footsteps.model.FootstepsPost;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
