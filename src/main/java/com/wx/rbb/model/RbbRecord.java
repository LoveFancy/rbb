package com.wx.rbb.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Table(name = "rbb_record")
@Data
public class RbbRecord implements Serializable {
    /**
     * ID

     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 儿童唯一ID
     */
    @Column(name = "child_id")
    private Integer childId;

    /**
     * 员工唯一ID
     */
    @Column(name = "empolyee_id")
    private Integer empolyeeId;

    /**
     * 调理方案

     */
    @Column(name = "recuperate_plan")
    private String recuperatePlan;

    /**
     * 调理师说
     */
    @Column(name = "emp_remark")
    private String empRemark;

    /**
     * 推荐食疗
     */
    @Column(name = "recommend_food")
    private String recommendFood;

    /**
     * 禁忌食物
     */
    @Column(name = "avoid_food")
    private String avoidFood;

    /**
     * 儿童姓名
     */
    @Column(name = "child_name")
    private String childName;

    /**
     * 调理项目
     */
    @Column(name = "recuperate_event")
    private String recuperateEvent;

    /**
     * 表征
     */
    private String present;

    /**
     * 调理时间

     */
    private Date time;

    /**
     * 评分，1-5颗星
     */
    private Integer stars;

    /**
     * 是否已经评价

     */
    @Column(name = "isAppraised")
    private Boolean isappraised;

    /**
     * 用户评价，最大500个字
     */
    private String appraise;

    private static final long serialVersionUID = 1L;
}