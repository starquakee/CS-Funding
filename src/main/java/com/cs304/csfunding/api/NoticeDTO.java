package com.cs304.csfunding.api;

import lombok.Data;
@Data
public class NoticeDTO {
    private String content;
    private String time;
    private int uuid;
    private int noticeFrom;
    private int noticeTo;
}
