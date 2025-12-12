package com.hmdp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginFormDTO {
    private String phone;
    private String code;
    private String password;
}
