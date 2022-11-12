package com.example.backend.entity.user;

import com.example.backend.dto.user.SignUpRealtorRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Realtor extends User {

    @Column
    private String profile;

    @Column
    private Long check;

    public Realtor(SignUpRealtorRequestDto dto) {
        super(dto);
        this.profile = dto.getProfile();
        this.check = 0L;
    }
}
