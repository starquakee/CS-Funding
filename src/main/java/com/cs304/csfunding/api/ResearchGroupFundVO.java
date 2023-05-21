package com.cs304.csfunding.api;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResearchGroupFundVO {
    @Data
    public static class FundOpVO {
        private Map<String, Object> value;
        private String label;
    }

    private int value;
    private String label;
    private List<FundOpVO> children;
}
