package com.example.backend.search.service;

import com.example.backend.comment.model.Comment;
import com.example.backend.consult.model.AnswerState;
import com.example.backend.consult.model.Consult;
import com.example.backend.consult.repository.ConsultRepository;
import com.example.backend.footsteps.model.FootstepsPost;
import com.example.backend.footsteps.repository.FootstepsRepository;
import com.example.backend.global.exception.customexception.AccessDeniedException;
import com.example.backend.global.exception.customexception.NotFoundException;
import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.search.dto.ConsultFootStepsResponseDto;
import com.example.backend.search.dto.MyConsultResponseDto;
import com.example.backend.search.exception.KeywordNotFoundException;
import com.example.backend.user.exception.user.UserUnauthorizedException;
import com.example.backend.user.model.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final ConsultRepository consultRepository;
    private final FootstepsRepository footstepsRepository;

    @Transactional(readOnly = true)
    public List<MyConsultResponseDto> searchConsult(UserDetailsImpl userDetails, String keyword) {
        validAuth(userDetails);

        List<Consult> consultList = consultRepository.findAllByUserId(userDetails.getUser().getId());
        List<MyConsultResponseDto> myConsultResponseDtoList = new ArrayList<>();
        for (Consult consult : consultList) {
            if(consult.getTitle().equals(keyword)){
                if(consult.getAnswerState().equals(AnswerState.WAIT)){
                    myConsultResponseDtoList.add(
                            MyConsultResponseDto.builder()
                                    .searchWord(keyword)
                                    .consultMessage(consult.getConsultMessage())
                                    .answerState(consult.getAnswerState())
                                    .createDate(consult.getCreateDate())
                                    .title(consult.getTitle())
                                    .build()
                    );
                }else{
                    myConsultResponseDtoList.add(
                            MyConsultResponseDto.builder()
                                    .searchWord(keyword)
                                    .comment(consult.getCommentList()
                                            .stream()
                                            .map(Comment::getContent)
                                            .collect(Collectors.toList()).toString())
                                    .answerState(consult.getAnswerState())
                                    .createDate(consult.getCreateDate())
                                    .title(consult.getTitle())
                                    .build()
                    );
                }
            } else if (myConsultResponseDtoList.isEmpty()) {
                throw new KeywordNotFoundException();
            }
        }
        return myConsultResponseDtoList;
    }

//    @Transactional(readOnly = true)
//    public List<ConsultFootStepsResponseDto> searchPremises(UserDetailsImpl userDetails, String keyword) {
//        validRealtor(userDetails);
//        FootstepsPost footstepsPost = footstepsRepository.findById(userDetails.getUser().getId()).orElseThrow(NotFoundException::new);
//        List<Consult> consultList = consultRepository.findAllByUserId(userDetails.getUser().getId());
//        List<ConsultFootStepsResponseDto> myConsultResponseDtoList = new ArrayList<>();
//
//
//        for (Consult consult : consultList) {
//            if (consult.getTitle().equals(keyword) && footstepsPost.getTitle().equals(keyword)) {
//                myConsultResponseDtoList.add(
//                        ConsultFootStepsResponseDto.builder()
//                                .answerState(consult.getAnswerState())
//                                .title(consult.getTitle())
//                                .review(footstepsPost.getReview())
//                                .overLab(true)
//                                .build()
//                );
//            } else if (consult.getTitle().equals(keyword) && footstepsPost.getTitle() == null) {
//                myConsultResponseDtoList.add(
//                        ConsultFootStepsResponseDto.builder()
//                                .overLab(false)
//                                .answerState(consult.getAnswerState())
//                                .review(consult.getConsultMessage())
//                                .title(consult.getTitle())
//                                .build()
//                );
//            } else if (consult.getTitle() == null && footstepsPost.getTitle().equals(keyword)) {
//                myConsultResponseDtoList.add(
//                        ConsultFootStepsResponseDto.builder()
//                                .title(footstepsPost.getTitle())
//                                .review(footstepsPost.getReview())
//                                .overLab(false)
//                                .build()
//                );
//            } else if (myConsultResponseDtoList.isEmpty()) {
//                throw new KeywordNotFoundException();
//            }
//
//        } return myConsultResponseDtoList;
//    }

    @Transactional(readOnly = true)
    public List<MyConsultResponseDto> waitCustomerSearch(UserDetailsImpl userDetails, String keyword) {
        validRealtor(userDetails);

        List<Consult> consultList = consultRepository.findAllByUserId(userDetails.getUser().getId());
        List<MyConsultResponseDto> myConsultResponseDtoList = new ArrayList<>();
        for (Consult consult : consultList) {
            if(consult.getTitle().equals(keyword) && consult.getAnswerState().equals(AnswerState.WAIT)){
                    myConsultResponseDtoList.add(
                            MyConsultResponseDto.builder()
                                    .searchWord(keyword)
                                    .consultMessage(consult.getConsultMessage())
                                    .answerState(consult.getAnswerState())
                                    .createDate(consult.getCreateDate())
                                    .title(consult.getTitle())
                                    .build()
                    );
            }else if (myConsultResponseDtoList.isEmpty()) {
                throw new KeywordNotFoundException();
            }
        }
        return myConsultResponseDtoList;
    }



    @Transactional(readOnly = true)
    public List<MyConsultResponseDto> repliedSearch(UserDetailsImpl userDetails, String keyword) {
        validRealtor(userDetails);

        List<Consult> consultList = consultRepository.findAllByUserId(userDetails.getUser().getId());
        List<MyConsultResponseDto> myConsultResponseDtoList = new ArrayList<>();
        for (Consult consult : consultList) {
            if(consult.getTitle().equals(keyword) && consult.getAnswerState().equals(AnswerState.ANSWER)){
                myConsultResponseDtoList.add(
                        MyConsultResponseDto.builder()
                                .searchWord(keyword)
                                .comment(consult.getCommentList()
                                        .stream()
                                        .map(Comment::getContent)
                                        .collect(Collectors.toList()).toString())
                                .answerState(consult.getAnswerState())
                                .createDate(consult.getCreateDate())
                                .title(consult.getTitle())
                                .build()
                );
            } else if (myConsultResponseDtoList.isEmpty()) {
                throw new KeywordNotFoundException();
            }
        }
        return myConsultResponseDtoList;
    }

    public void validAuth(UserDetailsImpl userDetails) {
        if (userDetails == null) throw new UserUnauthorizedException();
    }

    private void validRealtor(UserDetailsImpl userDetails){
        validAuth(userDetails);
        if(userDetails.getAuthority() != Authority.ROLE_REALTOR)
            throw new AccessDeniedException();
    }
}