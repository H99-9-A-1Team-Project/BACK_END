package com.example.backend.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {

    private static final String REALTOR_APPROVE_TITLE = "축하합니다, 등대지기 가입이 승인되었습니다.";
    private static final String REALTOR_REJECT_TITLE = "등대지기 가입이 거절되었습니다.";
    private static final String REALTOR_APPROVE_MESSAGE = "지금 등대지기의 서비스를 이용해보세요";
    private static final String REALTOR_REJECT_MESSAGE = "자세한 사항은 고객센터를 이용해주세요";

    private String address;
    private String title;
    private String content;

    public MailDto(String address) {
        this.address = address;
    }

    public void setRealtorApproveMessage(){
        this.title = REALTOR_APPROVE_TITLE;
        this.content = REALTOR_APPROVE_MESSAGE;
    }

    public void setRealtorRejectMessage(){
        this.title = REALTOR_REJECT_TITLE;
        this.content = REALTOR_REJECT_TITLE;

    }
}