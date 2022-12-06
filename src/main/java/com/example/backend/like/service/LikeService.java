package com.example.backend.like.service;

import com.example.backend.comment.model.Comment;
import com.example.backend.comment.repository.CommentRepository;
import com.example.backend.consult.exception.ConsultNotFoundException;
import com.example.backend.consult.model.Consult;
import com.example.backend.consult.repository.ConsultRepository;
import com.example.backend.global.exception.customexception.AccessDeniedException;
import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.like.model.Like;
import com.example.backend.like.repository.LikeRepository;
import com.example.backend.user.exception.user.UserUnauthorizedException;
import com.example.backend.user.model.Authority;
import com.example.backend.user.model.Realtor;
import com.example.backend.user.repository.RealtorRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;


    public void likeComment(Long id, UserDetailsImpl userDetails) {
        validUser(userDetails);
        Comment comment = commentRepository.findById(id).orElseThrow(ConsultNotFoundException::new);
        Like like = likeRepository.findByUserIdAndComment(userDetails.getUser().getId(), comment);
        Realtor realtor = comment.getRealtor();
        if(like == null){
            comment.update(comment.getLikeCount() + 1);
            realtor.update(realtor.getLikeCount() + 1);
            Like newLike = new Like(userDetails.getUser(),comment.getRealtor(),comment);
            likeRepository.save(newLike);
        return;

        }
        comment.update(comment.getLikeCount() - 1);
        realtor.update(realtor.getLikeCount() - 1);
        likeRepository.delete(like);
    }
    public void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
    }

    private void validUser(UserDetailsImpl userDetails){
        validAuth(userDetails);
        if(userDetails.getAuthority() != Authority.ROLE_USER)
            throw new AccessDeniedException();
    }
}
