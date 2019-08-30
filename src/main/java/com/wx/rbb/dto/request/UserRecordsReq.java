package com.wx.rbb.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRecordsReq {
    private String startTime;
    private String endTime;
    private Integer childId;
    private Integer pageNo;
    private Integer pageSize;
}
