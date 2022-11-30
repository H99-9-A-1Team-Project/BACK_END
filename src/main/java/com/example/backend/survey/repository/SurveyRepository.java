package com.example.backend.survey.repository;

import com.example.backend.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
