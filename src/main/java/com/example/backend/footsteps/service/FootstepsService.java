package com.example.backend.footsteps.service;

import com.example.backend.consult.repository.ConsultRepository;
import com.example.backend.footsteps.dto.FootstepsDetailResponseDto;
import com.example.backend.footsteps.dto.FootstepsRequstDto;
import com.example.backend.footsteps.repository.FootstepsRepository;
import com.example.backend.footsteps.repository.PhotoRepository;
import com.example.backend.global.S3.dto.AwsS3;
import com.example.backend.global.S3.service.AmazonS3Service;
import com.example.backend.global.config.auth.UserDetailsImpl;
import com.example.backend.global.entity.Consult;
import com.example.backend.global.entity.FootstepsPost;
import com.example.backend.global.entity.Photo;
import com.example.backend.global.exception.customexception.common.AccessDeniedException;
import com.example.backend.global.exception.customexception.common.ImageNotFoundException;
import com.example.backend.global.exception.customexception.common.NotFoundException;
import com.example.backend.global.exception.customexception.user.UserUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FootstepsService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3Service amazonS3Service;
    private final PhotoRepository photoRepository;

    private final FootstepsRepository footstepsRepository;
    private final ConsultRepository consultRepository;

    @Transactional
    public void createPost(List<MultipartFile> multipartFile, FootstepsRequstDto postRequestDto, UserDetailsImpl userDetails) throws IOException {
        validAuth(userDetails);

        FootstepsPost footstepsPost = saveFootStepPost(postRequestDto, userDetails);
        List<String> imgUrlList = amazonS3Service.uploadMultipleS3Photo(multipartFile, userDetails);

        savePhotos(footstepsPost, imgUrlList);
    }

    private void savePhotos(FootstepsPost footstepsPost, List<String> imgUrlList) {
        List<Photo> photos = new ArrayList<>();
        imgUrlList.forEach(imgUrl -> photos.add(new Photo(imgUrl, footstepsPost)));
        photoRepository.saveAll(photos);
    }


    private FootstepsPost saveFootStepPost(FootstepsRequstDto postRequestDto, UserDetailsImpl userDetails) {
        FootstepsPost footstepsPost = postRequestDto.toFootstepsPost(postRequestDto, userDetails);
        footstepsRepository.save(footstepsPost);
        return footstepsPost;
    }

    public List<FootstepsPost> getMyPosts(UserDetailsImpl userDetails) {
        validAuth(userDetails);
        return footstepsRepository.findByUser(userDetails.getUser());
    }

    public List<FootstepsPost> getMyAdviceRequest(UserDetailsImpl userDetails) {
        validAuth(userDetails);
        return footstepsRepository.findByUserInfo(userDetails.getUser().getId());
    }

    public FootstepsDetailResponseDto getFootstepDetail(Long premisesId, UserDetailsImpl userDetails) {
        validAuth(userDetails);
        boolean yesOrNo = false;

        FootstepsPost footstepsPost = footstepsRepository.findById(premisesId).orElseThrow(NotFoundException::new);
        Consult consult = consultRepository.findByCoordXAndCoordYAndUserId(footstepsPost.getCoordFX(),footstepsPost.getCoordFY(),userDetails.getUser().getId());

        if(!footstepsPost.getUser().getId().equals(userDetails.getUser().getId()))
            throw new AccessDeniedException();

        if(consult != null)
             yesOrNo= true;

        return FootstepsDetailResponseDto.builder()
            .title(footstepsPost.getTitle())
            .coordFX(footstepsPost.getCoordFX())
            .coordFY(footstepsPost.getCoordFY())
            .price(footstepsPost.getPrice())
            .size(footstepsPost.getSize())
            .review(footstepsPost.getReview())
            .sun(footstepsPost.isSun())
            .mold(footstepsPost.isMold())
            .vent(footstepsPost.isVent())
            .water(footstepsPost.isWater())
            .ventil(footstepsPost.isVentil())
            .drain(footstepsPost.isDrain())
            .draft(footstepsPost.isDraft())
            .extraMemo(footstepsPost.getExtraMemo())
            .option(footstepsPost.getOption())
            .destroy(footstepsPost.isDestroy())
            .utiRoom(footstepsPost.isUtiRoom())
            .securityWindow(footstepsPost.isSecurityWindow())
            .noise(footstepsPost.isNoise())
            .loan(footstepsPost.isLoan())
            .cctv(footstepsPost.isCctv())
            .hill(footstepsPost.isHill())
            .mart(footstepsPost.isMart())
            .hospital(footstepsPost.isHospital())
            .accessibility(footstepsPost.isAccessibility())
            .park(footstepsPost.isPark())
            .createdAt(footstepsPost.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
            .YesOrNo(yesOrNo)
            .build();
    }

    public List<Photo> getFootstepDetailImages(Long premisesId, UserDetailsImpl userDetails, Pageable pageable) {
        validAuth(userDetails);

        FootstepsPost post = footstepsRepository.findById(premisesId).orElseThrow(NotFoundException::new);
        return photoRepository.findAllByFootstepsPost(post, pageable).getContent();
    }

    public void validAuth(UserDetailsImpl userDetails){
        if(userDetails == null) throw new UserUnauthorizedException();
    }

}