package com.example.backend.consult.service;

import com.example.backend.comment.dto.CommentResponseDto;
import com.example.backend.comment.model.Comment;
import com.example.backend.comment.repository.CommentRepository;
import com.example.backend.consult.dto.request.PutDetailConsultRequestDto;
import com.example.backend.consult.dto.request.RegisterConsultRequestDto;
import com.example.backend.consult.dto.response.DetailConsultResponseDto;
import com.example.backend.consult.dto.response.RepliedConsultResponseDto;
import com.example.backend.consult.dto.response.UserAllConsultResponseDto;
import com.example.backend.consult.model.AnswerState;
import com.example.backend.consult.model.Consult;
import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.consult.repository.ConsultRepository;
import com.example.backend.global.exception.customexception.AccessDeniedException;
import com.example.backend.user.exception.user.MemberNotFoundException;
import com.example.backend.user.exception.user.UserUnauthorizedException;
import com.example.backend.user.model.Authority;
import com.example.backend.user.model.Realtor;
import com.example.backend.user.repository.RealtorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultService {

    private final ConsultRepository consultRepository;
    private final CommentRepository commentRepository;
    private final RealtorRepository realtorRepository;

    @Transactional
    public void registerConsult(UserDetailsImpl userDetails, RegisterConsultRequestDto dto) {
        validAuth(userDetails);
        Consult consult = dto.toConsultDto(dto, userDetails);
        consultRepository.save(consult);
    }

    public List<UserAllConsultResponseDto> allConsult(Long id) {
        List<Consult> consultList = consultRepository.findAllByUserId(id);
        return consultList.stream()
                .map(UserAllConsultResponseDto::new)
                .collect(Collectors.toList());
    }


    public List<UserAllConsultResponseDto> waitConsult(UserDetailsImpl userDetails) {
        validRealtor(userDetails);
        List<Consult> consultList = consultRepository.findAllByAnswerState(AnswerState.WAIT.ordinal());
        return consultList.stream()
                .map(UserAllConsultResponseDto::new)
                .collect(Collectors.toList());

    }
    public DetailConsultResponseDto detailConsult(Long consultId, UserDetailsImpl userDetails) {
        validAuth(userDetails);
        Consult consult = consultRepository.findById(consultId).orElseThrow();

        List<Comment> commentList = commentRepository.findAllByConsultId(consultId);
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

        List<Boolean> checkList = getConsultCheckList(consult);


        for(Comment comment: commentList){
            commentResponseDtos.add(
                    CommentResponseDto.builder()
                            .Id(comment.getId())
                            .nickname(comment.getRealtor().getNickname())
                            .profile(comment.getRealtor().getProfile())
                            .introMessage(comment.getRealtor().getIntroMessage())
                            .createdAt(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                            .answerMessage(comment.getContent())
                            .likeCount(comment.getLikeCount())
                            .realtorLike(comment.getRealtor().getLikeCount())
                            .build()
            );
        }
        return DetailConsultResponseDto.builder()
                .Id(consult.getId())
                .title(consult.getTitle())
                .coordX(consult.getCoordX())
                .coordY(consult.getCoordY())
                .answerState(consult.getAnswerState())
                .checks(checkList)
                .comments(commentResponseDtos)
                .createdAt(consult.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .build();


    }

    private static List<Boolean> getConsultCheckList(Consult consult) {
        List<Boolean> checkList = new ArrayList<>();
        checkList.add(consult.isCheck1());
        checkList.add(consult.isCheck2());
        checkList.add(consult.isCheck3());
        checkList.add(consult.isCheck4());
        checkList.add(consult.isCheck5());
        checkList.add(consult.isCheck6());
        return checkList;
    }

    public List<RepliedConsultResponseDto> getRepliedConsult(UserDetailsImpl userDetails) {
        validRealtor(userDetails);
        List<RepliedConsultResponseDto> result = new ArrayList<>();
        Realtor realtor = realtorRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(MemberNotFoundException::new);

        commentRepository.findByRealtor(realtor)
                .forEach(comment -> { if(comment.getConsult().getAnswerState() != AnswerState.WAIT ){
                    result.add(new RepliedConsultResponseDto(comment.getConsult(), comment.getContent()));
                }});

        return result;
    }
    @Transactional
    public DetailConsultResponseDto PutdetailConsult(Long consultId, PutDetailConsultRequestDto dto, UserDetailsImpl userDetails) {
        validUser(userDetails);
        Consult consult = consultRepository.findById(consultId).orElseThrow();
        List<Boolean> checkList = getConsultCheckList(consult);
        checkOwner(consult,userDetails);
        consult.updateState2(dto.getAnswerState());
        consultRepository.save(consult);
        ////
        List<Comment> commentList = commentRepository.findAllByConsultId(consultId);
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for(Comment comment: commentList){
            commentResponseDtos.add(
                    CommentResponseDto.builder()
                            .Id(comment.getId())
                            .nickname(comment.getRealtor().getNickname())
                            .profile(comment.getRealtor().getProfile())
                            .introMessage(comment.getRealtor().getIntroMessage())
                            .createdAt(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                            .answerMessage(comment.getContent())
                            .likeCount(comment.getLikeCount())
                            .realtorLike(comment.getRealtor().getLikeCount())
                            .build()
            );
        }
        return DetailConsultResponseDto.builder()
                .Id(consult.getId())
                .title(consult.getTitle())
                .coordX(consult.getCoordX())
                .coordY(consult.getCoordY())
                .answerState(consult.getAnswerState())
                .checks(checkList)
                .comments(commentResponseDtos)
                .createdAt(consult.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .build();


    }

    public void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
    }

    private void validRealtor(UserDetailsImpl userDetails){
        validAuth(userDetails);
        if(userDetails.getAuthority() != Authority.ROLE_REALTOR)
            throw new AccessDeniedException();
    }
    private void validUser(UserDetailsImpl userDetails){
        validAuth(userDetails);
        if(userDetails.getAuthority() != Authority.ROLE_USER)
            throw new AccessDeniedException();
    }
    private void checkOwner(Consult consult, UserDetailsImpl userDetails){
        if(!consult.checkOwnerByUserId(userDetails)){
            throw new IllegalArgumentException("회원님이 작성한 글이 아닙니다.");
        }
    }


}