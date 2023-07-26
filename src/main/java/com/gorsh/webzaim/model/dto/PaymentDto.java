package com.gorsh.webzaim.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDto{

    private int numberCredit;

    private String payment;

    private LocalDate currentDate;


}
