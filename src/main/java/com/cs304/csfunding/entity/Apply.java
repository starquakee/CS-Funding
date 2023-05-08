package com.cs304.csfunding.entity;

import lombok.Data;

@Data
public class Apply {
    private String Name;
    private int Money;
    private String Type1;
    private String Type2;
    private int FundID;
    private int ResearchGroupID;
    private int UserID;
    private String State;
    private String ContentSummary;
    private String Remark;
    private String Time;
    private int uuid;
}
