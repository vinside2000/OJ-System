package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * date: 2021/8/9 15:20
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("student")
public class Student {
    private String school;
    private String major;
    private String studentClass;
    private String number;
    private String name;
    private String gender;
    private String username;
    private String password;
    @TableId(value = "uuid")
    private String uuid;
}
