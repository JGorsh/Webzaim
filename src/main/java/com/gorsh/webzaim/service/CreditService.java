package com.gorsh.webzaim.service;

import com.gorsh.webzaim.model.domain.Credit;
import com.gorsh.webzaim.repository.CreditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;

    public List<Credit> getAllCredit(){
        return creditRepository.findAll();
    }
}
