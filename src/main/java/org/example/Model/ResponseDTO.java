package org.example.Model;

import lombok.*;

@Data

public class ResponseDTO {

    private String rqUID;
    private String clientId;
    private String account;
    private String openDate;
    private String closeDate;
    private String currency;
    private Double balance;
    private Double maxLimit;
}
