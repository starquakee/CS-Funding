package com.cs304.csfunding.entity;

import lombok.Data;

@Data
public class Fund {
    private int uuid;
    private String FundNumber;
    private String FundName;
    private float Sum;
    private float Balance;
    private int RemainDays;
    private String StartTime;
    private String EndTime;
}
