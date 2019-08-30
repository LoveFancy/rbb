package com.wx.rbb.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Table(name = "rbb_present")
@Data
public class RbbPresent implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 表征
     */
    private String present;

    private static final long serialVersionUID = 1L;
}