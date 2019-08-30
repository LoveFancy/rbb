package com.wx.rbb.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRes {
    private ResultDto result;
    private UserInfo data;


    @Data
    @NoArgsConstructor
    public static class UserInfo{
        private Integer userId;
        private String openId;
        private String sessionKey;
        private String phone;
    }
}
