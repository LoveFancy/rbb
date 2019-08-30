package com.wx.rbb.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Table(name = "rbb_child_info")
@Data
public class RbbChildInfo implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户唯一ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用户名


     */
    private String name;

    /**
     * 用户生日

     */
    private String birthday;

    /**
     * 用户性别, 1:男   2:女
     */
    private String gender;

    /**
     * 体重，单位Kg
     */
    private String weight;

    /**
     * BMI结果
     */
    private String bmi;

    /**
     * 最后更新时间
     */
    @Column(name = "last_updated_ts")
    private Date lastUpdatedTs;

    /**
     * 最后更新人
     */
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    private static final long serialVersionUID = 1L;
}