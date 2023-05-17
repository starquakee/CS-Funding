package com.cs304.csfunding.entity;

import lombok.Data;
@Data
public class Notice {
    private String content;
    private String time;
    private int uuid;
    private int noticeFrom;
    private int noticeTo;
}
