package com.wx.rbb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.wx.rbb.dao.*;
import com.wx.rbb.dto.request.ChildListReq;
import com.wx.rbb.dto.request.ChildReq;
import com.wx.rbb.dto.request.UserRecordsReq;
import com.wx.rbb.dto.response.ChildListRes;
import com.wx.rbb.dto.response.ResultDto;
import com.wx.rbb.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rbb/app")
@Api(value = "儿童")
@Slf4j
public class ChildInfoController {

    @Autowired
    private RbbChildInfoMapper childInfoMapper;

    @Autowired
    private RbbRecordMapper recordMapper;

    @Autowired
    private RbbRecuperateMapper recuperateMapper;

    @Autowired
    private RbbPresentMapper presentMapper;

    @Autowired
    private RbbEmpolyeeMapper empolyeeMapper;

    @RequestMapping(value = "queryChildList", method = RequestMethod.POST)
    @ApiOperation("查询客户关联的儿童列表")
    public ChildListRes queryChildList(@RequestBody @NotNull ChildListReq reqObj) {
        ChildListRes resObj = new ChildListRes();
        RbbChildInfo record = new RbbChildInfo();
        record.setUserId(reqObj.getUserId());
        List<RbbChildInfo> childList = childInfoMapper.select(record);
        List<ChildListRes.ChildInfo> dataList = new ArrayList<>();
        for (RbbChildInfo child : childList) {
            ChildListRes.ChildInfo info = new ChildListRes.ChildInfo();
            info.setChildId(child.getId());
            info.setChildName(child.getName());
            dataList.add(info);
        }
        resObj.setResult(ResultDto.getSuccess());
        resObj.setData(dataList);
        return resObj;
    }


    @RequestMapping(value = "queryChildBaseInfo", method = RequestMethod.POST)
    @ApiOperation("查看儿童基本信息")
    public JSONObject queryChildBaseInfo(@RequestBody @NotNull ChildReq reqObj) {
        JSONObject resObj = new JSONObject();

        RbbChildInfo rbbChildInfo = childInfoMapper.selectByPrimaryKey(reqObj.getChildId());
        resObj.put("data", rbbChildInfo);
        resObj.put("result", ResultDto.getSuccess());
        return resObj;
    }


    @RequestMapping(value = "queryUserRecords", method = RequestMethod.POST)
    @ApiOperation("查询儿童调理记录")
    public JSONObject queryUserRecords(@RequestBody @NotNull UserRecordsReq reqObj) {
        JSONObject resObj = new JSONObject();
        Example example = new Example(RbbRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("childId", reqObj.getChildId());
        criteria.andGreaterThan("time", reqObj.getStartTime());
        criteria.andLessThan("time", reqObj.getEndTime());
        PageHelper.startPage(reqObj.getPageNo(), reqObj.getPageSize());
        int total = recordMapper.selectCountByExample(example);
        List<RbbRecord> rbbRecords = recordMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(rbbRecords)) {
            List<RbbRecuperate> rbbRecuperates = recuperateMapper.selectAll();
            Map<Integer, String> recuperateMap = new HashMap<>();
            for (RbbRecuperate rbbRecuperate : rbbRecuperates) {
                recuperateMap.put(rbbRecuperate.getId(), rbbRecuperate.getItem());
            }
            rbbRecords.forEach(record -> {
                String recuperateEvent = record.getRecuperateEvent();
                String[] recuperateArr = recuperateEvent.split(",");
                String[] itemArr = new String[recuperateArr.length];
                for (int i = 0; i < recuperateArr.length; i++) {
                    itemArr[i] = recuperateMap.get(recuperateArr[i]);
                }
                record.setRecuperateEvent(StringUtils.join(itemArr, ","));
            });
        }
        resObj.put("total", total);
        resObj.put("data", rbbRecords);
        resObj.put("result", ResultDto.getSuccess());
        return resObj;
    }


    @RequestMapping(value = "queryRecordDetail", method = RequestMethod.GET)
    @ApiOperation("查询调理记录的详情信息")
    public JSONObject queryRecordDetail(@RequestParam Integer recordId) {
        JSONObject resObj = new JSONObject();
        RbbRecord rbbRecord = recordMapper.selectByPrimaryKey(recordId);
        List<RbbRecuperate> rbbRecuperates = recuperateMapper.selectAll();
        Map<Integer, String> recuperateMap = new HashMap<>();
        for (RbbRecuperate rbbRecuperate : rbbRecuperates) {
            recuperateMap.put(rbbRecuperate.getId(), rbbRecuperate.getItem());
        }
        String recuperateEvent = rbbRecord.getRecuperateEvent();
        String[] recuperateArr = recuperateEvent.split(",");
        String[] itemArr = new String[recuperateArr.length];
        for (int i = 0; i < recuperateArr.length; i++) {
            itemArr[i] = recuperateMap.get(recuperateArr[i]);
        }
        rbbRecord.setRecuperateEvent(StringUtils.join(itemArr, ","));

        List<RbbPresent> rbbPresents = presentMapper.selectAll();
        Map<Integer, String> presentMap = new HashMap<>();
        for (RbbPresent rbbPresent : rbbPresents) {
            presentMap.put(rbbPresent.getId(), rbbPresent.getPresent());
        }
        String present = rbbRecord.getPresent();
        String[] presentArr = present.split(",");
        String[] presentNameArr = new String[presentArr.length];
        for (int i = 0; i < presentArr.length; i++) {
            presentNameArr[i] = presentMap.get(presentArr[i]);
        }
        rbbRecord.setPresent(StringUtils.join(presentNameArr, ","));
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(rbbRecord));
        RbbEmpolyee rbbEmpolyee = empolyeeMapper.selectByPrimaryKey(rbbRecord.getEmpolyeeId());
        jsonObject.put("employeeName", rbbEmpolyee.getName());
        jsonObject.put("employTitle", rbbEmpolyee.getTitle());
        jsonObject.put("employPhoto", rbbEmpolyee.getPhoto());
        resObj.put("data", jsonObject);
        resObj.put("result", ResultDto.getSuccess());
        return resObj;
    }


    @RequestMapping(value = "queryRecuperateTotal", method = RequestMethod.POST)
    @ApiOperation("儿童调理项目统计")
    public JSONObject queryRecuperateTotal(@RequestBody @NotNull UserRecordsReq reqObj) {
        JSONObject resObj = new JSONObject();
        Example example = new Example(RbbRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("childId", reqObj.getChildId());
        criteria.andGreaterThan("time", reqObj.getStartTime());
        criteria.andLessThan("time", reqObj.getEndTime());
        List<RbbRecord> rbbRecords = recordMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(rbbRecords)) {
            List<RbbRecuperate> rbbRecuperates = recuperateMapper.selectAll();
            Map<Integer, String> recuperateMap = new HashMap<>();
            for (RbbRecuperate rbbRecuperate : rbbRecuperates) {
                recuperateMap.put(rbbRecuperate.getId(), rbbRecuperate.getItem());
            }
            List<String> recuperateList = new ArrayList<>();
            rbbRecords.forEach(record -> {
                String recuperateEvent = record.getRecuperateEvent();
                String[] recuperateArr = recuperateEvent.split(",");
                String[] itemArr = new String[recuperateArr.length];
                for (int i = 0; i < recuperateArr.length; i++) {
                    recuperateList.add(recuperateArr[i]);
                }
            });
            Map<String, List<String>> recuperateTotalMap = recuperateList.stream().collect(Collectors.groupingBy(recuperate -> recuperate));
            final JSONArray arr = new JSONArray();
            recuperateTotalMap.forEach((key,value) -> {
                JSONObject obj = new JSONObject();
                obj.put("name",recuperateMap.get(Integer.valueOf(key)));
                obj.put("num",value.size());
                arr.add(obj);
            });
            resObj.put("data", arr);
        }
        resObj.put("result", ResultDto.getSuccess());
        return resObj;
    }
    @RequestMapping(value = "queryPresentTotal", method = RequestMethod.POST)
    @ApiOperation("儿童表征统计")
    public JSONObject queryPresentTotal(@RequestBody @NotNull UserRecordsReq reqObj) {
        JSONObject resObj = new JSONObject();
        Example example = new Example(RbbRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("childId", reqObj.getChildId());
        criteria.andGreaterThan("time", reqObj.getStartTime());
        criteria.andLessThan("time", reqObj.getEndTime());
        List<RbbRecord> rbbRecords = recordMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(rbbRecords)) {
            List<RbbPresent> rbbPresents = presentMapper.selectAll();
            Map<Integer, String> presentMap = new HashMap<>();
            for (RbbPresent rbbPresent : rbbPresents) {
                presentMap.put(rbbPresent.getId(), rbbPresent.getPresent());
            }

            List<String> presentList = new ArrayList<>();
            rbbRecords.forEach(record -> {
                String present = record.getPresent();
                String[] presentArr = present.split(",");
                String[] presentNameArr = new String[presentArr.length];
                for (int i = 0; i < presentArr.length; i++) {
                    presentList.add(presentArr[i]);
                }

            });
            Map<String, List<String>> recuperateTotalMap = presentList.stream().collect(Collectors.groupingBy(present -> present));
            final List<JSONObject> resList = new ArrayList<>();
            recuperateTotalMap.forEach((key,value) -> {
                JSONObject obj = new JSONObject();
                obj.put("name",presentMap.get(Integer.valueOf(key)));
                obj.put("num",value.size());
                resList.add(obj);
            });
            resList.sort((a,b) ->{
                return a.getInteger("num").compareTo(b.getInteger("num"));
            });
            if (resList.size() > 5){
                resObj.put("data", resList.subList(0,5));
            }else{
                resObj.put("data", resList);
            }
            resObj.put("data", resList);
        }
        resObj.put("result", ResultDto.getSuccess());
        return resObj;
    }
}
