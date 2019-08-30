package com.wx.rbb.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Table(name = "rbb_user_info")
@Data
public class RbbUserInfo implements Serializable {
    /**
     * 用户唯一ID
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 绑定的手机号
     */
    @Column(name = "bind_phone")
    private String bindPhone;

    /**
     * 绑定的微信号

     */
    @Column(name = "bind_open_id")
    private String bindOpenId;

    private static final long serialVersionUID = 1L;
}