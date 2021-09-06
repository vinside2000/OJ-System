package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;

/**
 * Description:
 * date: 2021/8/17 16:32
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@TableName("teacher_course")
public class TeacherCourse {
    @TableId(value = "id")
    private int id;
    private String teacherUuid;
    private String courseUuid;

    public TeacherCourse(String teacherUuid, String courseUuid) {
        this.teacherUuid = teacherUuid;
        this.courseUuid = courseUuid;
    }
}
