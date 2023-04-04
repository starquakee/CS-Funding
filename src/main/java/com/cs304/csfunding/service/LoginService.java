package com.cs304.csfunding.service;

import com.cs304.csfunding.api.LoginDTO;
import com.cs304.csfunding.api.Result;

public interface LoginService {
    public Result login(LoginDTO loginDTO);
}
