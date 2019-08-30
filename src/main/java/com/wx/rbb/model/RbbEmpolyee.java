package com.wx.rbb.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Table(name = "rbb_empolyee")
@Data
public class RbbEmpolyee implements Serializable {
    /**
     * 员工唯一ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 员工姓名

     */
    private String name;

    /**
     * 员工生日
     */
    private String birthday;

    /**
     * 员工头衔

     */
    private String title;

    private String desc;

    /**
     * 员工照片
     */
    private byte[] photo;

    private static final long serialVersionUID = 1L;
}