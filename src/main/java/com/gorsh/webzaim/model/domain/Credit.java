package com.gorsh.webzaim.model.domain;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
@Getter
@Setter
public class Credit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payment;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date start;


}
