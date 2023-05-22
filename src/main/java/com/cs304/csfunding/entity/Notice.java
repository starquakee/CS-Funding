package com.cs304.csfunding.entity;

import lombok.Data;
@Data
public class Notice {
    private String content;
    private String Time;
    private int uuid;
    private int noticefrom;
    private int noticeto;
}
