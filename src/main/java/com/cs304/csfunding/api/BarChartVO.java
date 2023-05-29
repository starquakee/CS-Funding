package com.cs304.csfunding.api;

import lombok.Data;

@Data
public class BarChartVO {
    public BarChartVO(int name, float value){
        this.name = name;
        this.value = value;
    }
    private int name;
    private float value;
}
