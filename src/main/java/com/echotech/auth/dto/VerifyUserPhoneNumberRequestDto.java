package com.echotech.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor 
public class VerifyUserPhoneNumberRequestDto {
    private String userPhoneNumber;
}