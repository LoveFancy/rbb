package com.wx.rbb.dao;

import com.wx.rbb.mapper.MyMapper;
import com.wx.rbb.model.RbbRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RbbRecordMapper extends MyMapper<RbbRecord> {
}