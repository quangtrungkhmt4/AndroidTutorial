package com.benjamin.manga.response;

import com.benjamin.manga.constant.ResponseCode;
import com.benjamin.manga.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEntity {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("data")
    private AbstractResponse data;

    public ResponseEntity(Integer code) {
        this.code = code;
    }

    public ResponseEntity(AbstractResponse data) {
        this.code = ResponseCode.SUCCESS;
        this.data = data;
    }

    @Override
    public String toString() {
        return JsonUtil.castFromObject(this);
    }
}
