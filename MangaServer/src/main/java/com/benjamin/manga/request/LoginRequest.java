package com.benjamin.manga.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest  extends AbstractRequest{
    private String email;
    private String password;
}
