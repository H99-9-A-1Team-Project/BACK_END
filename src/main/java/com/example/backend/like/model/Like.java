package com.example.backend.like.model;

import com.example.backend.comment.model.Comment;
import com.example.backend.consult.model.Consult;
import com.example.backend.user.model.Realtor;
import com.example.backend.user.model.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Like")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User user;

    @JoinColumn(name = "realtor_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Realtor realtor;

    @JoinColumn(name = "consult_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Comment comment;

    public Like(User user, Realtor realtor, Comment comment) {
        this.user = user;
        this.realtor = realtor;
        this.comment = comment;

    }


}
