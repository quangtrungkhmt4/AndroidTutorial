package com.benjamin.manga.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse extends AbstractResponse {
    private String userId;
    private String userName;
    private String token;
}
