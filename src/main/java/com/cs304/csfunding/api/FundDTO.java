package com.cs304.csfunding.api;

import lombok.Data;

@Data
public class FundDTO {
    private String fundNumber;
    private String fundName;
    private float sum;
    private float balance;
    private int remainDays;
    private String startTime;
    private String endTime;
    private int uuid;
}
