package com.wx.rbb.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChildListRes {

    private ResultDto result;
    private List<ChildInfo> data;

    @Data
    @NoArgsConstructor
    public static class ChildInfo{
        private Integer childId;
        private String childName;
    }
}
