package com.gorsh.webzaim.service;

import com.gorsh.webzaim.model.domain.Credit;
import com.gorsh.webzaim.model.dto.PaymentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CreditsHandler {
    public static final ArrayList<String> listPaymentCodes = new ArrayList<>(Arrays.asList("X","0","1","A","2","3"));
    public Map<LocalDate, String> getThroughLine(List<Credit> creditList){
        Map<LocalDate, String> throughLineMap = initPaymentMap(creditList);
        List<PaymentDto> generalPaymentList = getPaymentList(creditList);
        for (PaymentDto paymentDto : generalPaymentList){
            String value = throughLineMap.get(paymentDto.getCurrentDate());
            int ordMap = listPaymentCodes.indexOf(value);
            int ordList = listPaymentCodes.indexOf(paymentDto.getPayment());
            if(ordMap<ordList){
                throughLineMap.put(paymentDto.getCurrentDate(), paymentDto.getPayment());
            }
        }
        return throughLineMap;
    }

    public LocalDate getCurrentLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDate currentDate = LocalDate.parse(date, formatter);
        return currentDate;
    }

    public List<PaymentDto> getPaymentList(List<Credit> credits){
        List<PaymentDto> paymentDtoList = new ArrayList();
        int creditCount = 0;
        for (Credit credit : credits){
            String payments = new StringBuilder(credit.getPayment()).reverse().toString();
            char[] payCharMas = payments.toCharArray();
            for(int i=0; i<payCharMas.length; i++){
                PaymentDto paymentDto = new PaymentDto();
                paymentDto.setPayment(String.valueOf(payCharMas[i]));
                paymentDto.setCurrentDate(getCurrentLocalDate(credit.getStart().toString()).plusMonths(i));
                paymentDto.setNumberCredit(creditCount);
                paymentDtoList.add(paymentDto);
            }
            creditCount++;
        }
        return paymentDtoList;
    }

    public Map<LocalDate, String> initPaymentMap(List<Credit> creditList){
        List<Credit> sortList = creditList.stream().sorted(Comparator.comparing(Credit::getStart)).collect(Collectors.toList());
        LocalDate startDate = getCurrentLocalDate(sortList.get(0).getStart().toString());
        LocalDate endDate = getCurrentLocalDate((sortList.get(sortList.size()-1).getStart()).toString());
        Period period = Period.between(startDate, endDate) ;
        int amountLastMonth = sortList.get(sortList.size()-1).getPayment().toCharArray().length;
        int amountAllMonth = period.getMonths() + amountLastMonth;
        Map<LocalDate, String> map = new TreeMap<>();
        for(int i =0; i < amountAllMonth; i++){
            map.put(startDate.plusMonths(i), "X");
        }
        return map;
    }
}
