package com.example.backend.footsteps.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterPhotoRequest {
    private Photoprofile photoprofileList;
    private PhotoListRequestDto photoListRequestDto;

}
