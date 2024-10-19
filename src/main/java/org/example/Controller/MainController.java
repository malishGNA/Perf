package org.example.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Model.RequestDTO;
import org.example.Model.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@RestController
public class MainController {
    private final Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object getAccountInfo(@RequestBody RequestDTO requestDTO) {
        try{
            String clientId = requestDTO.getClientId();
            String rqUID = requestDTO.getRqUID();
            String account = requestDTO.getAccount();
            String openDate = requestDTO.getOpenDate();
            String closeDate = requestDTO.getCloseDate();

            String currency;
            double maxLimit;

            // Логика для выбора валюты и максимального лимита
            if (clientId.startsWith("8")) {
                currency = "US";
                maxLimit = 2000.00;
            } else if (clientId.startsWith("9")){
                currency = "EU";
                maxLimit = 1000.00;
            }else {
                currency = "RUB";
                maxLimit = 10000.00;
            }




            // Генерация случайного баланса
            Random random = new Random();
            double balance = Math.round(random.nextDouble() * maxLimit * 100.0) / 100.0;

            ResponseDTO responseDTO = new ResponseDTO();

            // Формирование ответа
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(account);
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);
            responseDTO.setRqUID(rqUID);
            responseDTO.setOpenDate(openDate);
            responseDTO.setCloseDate(closeDate);

            log.info("********** RequestDTO **********\n{}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.info("********** ResponseDTO **********\n{}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return ResponseEntity.ok(responseDTO);

        }catch (Exception e){
            log.error("Ошибка при обработке запроса", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

