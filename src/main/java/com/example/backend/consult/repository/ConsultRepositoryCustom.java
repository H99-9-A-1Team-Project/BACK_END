package com.example.backend.consult.repository;

import com.example.backend.consult.model.Consult;

import java.util.List;

//메서드를 작성하는 곳
public interface ConsultRepositoryCustom {
    List<Consult> findProductByUserId(Long userId);
}
