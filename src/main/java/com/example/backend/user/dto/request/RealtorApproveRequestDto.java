package com.example.backend.user.dto.request;

import com.example.backend.user.model.AccountCheck;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RealtorApproveRequestDto {
    private AccountCheck accountCheck;
    private String email;
}
