package com.gorsh.webzaim.repository;

import com.gorsh.webzaim.model.domain.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findAll();
}
