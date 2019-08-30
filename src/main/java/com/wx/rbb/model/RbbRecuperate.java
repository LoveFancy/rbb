package com.wx.rbb.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Table(name = "rbb_recuperate")
@Data
public class RbbRecuperate implements Serializable {
    /**
     * 调理项目ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 调理项目名称

     */
    private String item;

    private static final long serialVersionUID = 1L;
}