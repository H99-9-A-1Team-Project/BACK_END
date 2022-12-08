package com.example.backend.footsteps.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PhotoListRequestDto {
    private MultipartFile sunImg;
    private MultipartFile moldImg;
    private MultipartFile ventImg;
    private MultipartFile waterImg;
    private MultipartFile ventilImg;
    private MultipartFile drainImg;
    private MultipartFile draftImg;
    private MultipartFile destroyImg;
    private MultipartFile utiRoomImg;
    private MultipartFile securityWindowImg;
    private MultipartFile noiseImg;
    private MultipartFile loanImg;
    private MultipartFile cctvImg;
    private MultipartFile hillImg;
    private MultipartFile martImg;
    private MultipartFile hospitalImg;
    private MultipartFile accessibilityImg;
    private MultipartFile parkImg;
}
