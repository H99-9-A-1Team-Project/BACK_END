package com.example.backend.global.config.jwt;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class VerifyResult {

    private boolean success;
    private String userId;


}
