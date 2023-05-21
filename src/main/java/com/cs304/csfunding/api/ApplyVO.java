package com.cs304.csfunding.api;

import lombok.Data;

@Data
public class ApplyVO {
    String fundName;
    String researchGroup;
    String applyPerson;
    float money;
    String state;
    String type1;
    String type2;
    String summary;
    String remark;
    int fundNumber;
}
