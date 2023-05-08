package com.cs304.csfunding.api;

import lombok.Data;

@Data
public class ApplyDTO {
    private String name;
    private int money;
    private String type1;
    private String type2;
    private int fundID;
    private int researchGroupID;
    private int userID;
    private String state;
    private String contentSummary;
    private String remark;
    private int UUID;
}
