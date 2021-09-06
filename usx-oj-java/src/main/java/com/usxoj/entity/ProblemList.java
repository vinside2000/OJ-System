package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 * date: 2021/8/19 15:08
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
@TableName("problem_list")
public class ProblemList {
    @TableId(value = "uuid")
    private String uuid;
    private String name;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    @TableField(exist = false)
    private int proCount;
}
