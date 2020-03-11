package com.benjamin.manga.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends AbstractRequest {

    private String email;
    private String password;
    private String name;

}
