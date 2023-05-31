package com.cs304.csfunding.api;

import lombok.Data;

@Data
public class RegisterDTO {
  private String userId;
  private String name;
  private String register_key;
  private String validCode;
  private String reg;
}
