package com.wx.rbb.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class ResultDto {
    private int code;
    private String msg;

    public static ResultDto getSuccess(){
        ResultDto result = new ResultDto();
        result.setCode(0);
        result.setMsg("success");
        return result;
    }
}
