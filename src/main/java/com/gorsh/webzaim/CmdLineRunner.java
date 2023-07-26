package com.gorsh.webzaim;

import com.gorsh.webzaim.model.domain.Credit;
import com.gorsh.webzaim.model.dto.PaymentDto;
import com.gorsh.webzaim.service.CreditService;
import com.gorsh.webzaim.service.CreditsHandler;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CmdLineRunner implements CommandLineRunner {

    CreditsHandler creditsHandler;

    CreditService creditService;

    private List<Credit> creditList;

    @Override
    public void run(String... args) throws Exception {
        creditList = creditService.getAllCredit();
        System.out.print(getLogConsole(creditsHandler.getThroughLine(creditList)));
    }

    public String getLogConsole(Map<LocalDate, String> paymentsMap){
        String str = "           ";
        StringBuilder stringBuilder = new StringBuilder("\n\n");
        for(Map.Entry<LocalDate, String> map : paymentsMap.entrySet()){
            stringBuilder.append(map.getKey().toString() + "  ");
        }
        stringBuilder.append("\n\n    ");
        int count=0;
        List<Credit> sortList = creditList.stream().sorted(Comparator.comparing(Credit::getStart)).collect(Collectors.toList());
        List<PaymentDto> paymentDtoList = creditsHandler.getPaymentList(sortList);
        LocalDate start = paymentDtoList.get(0).getCurrentDate();
        LocalDate startNext;
        for (int i=0; i<paymentDtoList.size();i++){
            int numberCredit = paymentDtoList.get(i).getNumberCredit();
            if (numberCredit==count){
                stringBuilder.append(paymentDtoList.get(i).getPayment()+ str);
            }
            else {
                stringBuilder.append("\n\n    ");
                startNext = paymentDtoList.get(i).getCurrentDate();
                Period period = Period.between(start, startNext);
                stringBuilder.append((" " + str).repeat(period.getMonths()));
                stringBuilder.append(paymentDtoList.get(i).getPayment()+ "           ");
                count=numberCredit;
            }
        }

        stringBuilder.append("\n\n    ");
        for(Map.Entry<LocalDate, String> map : paymentsMap.entrySet()){
            stringBuilder.append(map.getValue() + "           ");
        }
        stringBuilder.append("\n\n\n");
        return stringBuilder.toString();
    }
}
