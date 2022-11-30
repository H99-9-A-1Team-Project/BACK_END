package com.example.backend.survey.dto;

import com.example.backend.survey.domain.Survey;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSurveyRequestDto {

    private boolean check1;
    private boolean check2;
    private boolean check3;
    private String surveyMessage;

    public Survey toSurvey() {
        return Survey.builder()
                .check1(check1)
                .check2(check2)
                .check3(check3)
                .surveyMessage(surveyMessage)
                .build();
    }
}
