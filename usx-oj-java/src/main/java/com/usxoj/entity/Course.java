package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description:
 * date: 2021/8/13 20:43
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("course")
public class Course {
    @TableId(value = "course_uuid")
    private String courseUuid;
    private String courseName;
    private String courseImg;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date courseDate;

    //    @TableField(exist = false)
//    private Teacher teacher;
}
