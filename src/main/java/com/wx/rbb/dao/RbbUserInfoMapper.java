package com.wx.rbb.dao;

import com.wx.rbb.mapper.MyMapper;
import com.wx.rbb.model.RbbUserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RbbUserInfoMapper extends MyMapper<RbbUserInfo> {
}