package com.example.isa.repository;

import com.example.isa.model.Answer;
import com.example.isa.model.BloodDonor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    List<Answer> findAllByBloodDonor(BloodDonor bloodDonor);
}
