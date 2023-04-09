package com.cs304.csfunding.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Fund {
    private String FundNumber;
    private String FundName;
    private float Sum;
    private float Balance;
    private Date StartTime;
    private Date EndTime;
    private int RemainDays;
}
