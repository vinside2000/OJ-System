package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * Description:
 * date: 2021/8/13 8:11
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
@TableName("teacher")
public class Teacher {
    @TableId(value = "uuid")
    private String uuid;
    private String school;
    private String jobNumber;
    private String name;
    private String gender;
    private String username;
    private String password;
    @TableField(exist = false)
    private List<Course> courses;
}
