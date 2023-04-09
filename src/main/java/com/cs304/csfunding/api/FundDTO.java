package com.cs304.csfunding.api;

import lombok.Data;

import java.util.Date;

@Data
public class FundDTO {
    private String FundNumber;
    private String FundName;
    private float Sum;
    private float Balance;
    private int RemainDays;


}
