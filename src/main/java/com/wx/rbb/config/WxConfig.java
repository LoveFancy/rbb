package com.wx.rbb.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WxConfig {

    @Autowired
    private WxProperties wxProperties;

    @Bean(name="wxMaService")
    public WxMaService wxMaService(){
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wxProperties.getAppid());
        config.setSecret(wxProperties.getSecret());
        config.setToken(wxProperties.getToken());
        config.setAesKey(wxProperties.getAesKey());
        config.setMsgDataFormat(wxProperties.getMsgDataFormat());
        WxMaService wxService = new WxMaServiceImpl();
        wxService.setWxMaConfig(config);
        return wxService;
    }
}
