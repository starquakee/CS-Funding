package com.cs304.csfunding.entity;

import lombok.Data;

@Data
public class User {
    private int uuid;
    private String ID;
    private String Key;
    private boolean IsAdmin;
}
