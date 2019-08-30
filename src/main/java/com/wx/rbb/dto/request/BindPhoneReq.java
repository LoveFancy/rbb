package com.wx.rbb.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BindPhoneReq {
    private int userId;
    private String phone;
}
