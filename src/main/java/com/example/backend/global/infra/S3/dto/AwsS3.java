package com.example.backend.global.infra.S3.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AwsS3 {
    private String key;
    private String path;

    @Builder
    public AwsS3(String key, String path) {
        this.key = key;
        this.path = path;
    }
}