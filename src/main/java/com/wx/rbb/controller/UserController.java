package com.wx.rbb.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.wx.rbb.dao.RbbUserInfoMapper;
import com.wx.rbb.dto.request.BindPhoneReq;
import com.wx.rbb.dto.response.LoginRes;
import com.wx.rbb.dto.response.ResultDto;
import com.wx.rbb.model.RbbUserInfo;
import com.wx.rbb.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rbb/app")
@Api(value = "客户")
@Slf4j
public class UserController {

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private RbbUserInfoMapper userInfoMapper;

    /**
     * 登陆接口
     */
    @GetMapping("/login")
    public LoginRes login(String code) {
        LoginRes result = new LoginRes();
        ResultDto res = new ResultDto();
        if (StringUtils.isBlank(code)) {
            res.setCode(1);
            res.setMsg("code不能为空.");
            result.setResult(res);
            return result;
        }
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
            RbbUserInfo record = new RbbUserInfo();
            record.setBindOpenId(session.getOpenid());
            RbbUserInfo rbbUserInfo = userInfoMapper.selectOne(record);
            if (rbbUserInfo == null){
                rbbUserInfo = new RbbUserInfo();
                rbbUserInfo.setBindOpenId(session.getOpenid());
                userInfoMapper.insertSelective(rbbUserInfo);
            }
            LoginRes.UserInfo userInfo = new LoginRes.UserInfo();
            userInfo.setOpenId(session.getOpenid());
            userInfo.setPhone(rbbUserInfo.getBindPhone());
            userInfo.setSessionKey(session.getSessionKey());
            userInfo.setUserId(rbbUserInfo.getUserId());
            result.setResult(ResultDto.getSuccess());
            result.setData(userInfo);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            res.setCode(2);
            res.setMsg("登录失败.");
            result.setResult(res);
        }
        return result;
    }

    @RequestMapping(value = "bindPhone", method = RequestMethod.POST)
    @ApiOperation("绑定手机号")
    public ResultDto bindPhone(@RequestBody BindPhoneReq reqObj) {
        RbbUserInfo userInfo = new RbbUserInfo();
        userInfo.setUserId(reqObj.getUserId());
        userInfo.setBindPhone(reqObj.getPhone());
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
        return ResultDto.getSuccess();
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/info")
    public String info(String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(userInfo);
    }


    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone( String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(phoneNoInfo);
    }




}
