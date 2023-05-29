package com.cs304.csfunding.api;

import lombok.Data;

@Data
public class FundInsertDTO {
    private String fundNumber;
    private String fundName;
    private float sum;
    private String startTime;
    private String endTime;
    private int researchGroup;
}
