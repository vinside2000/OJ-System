package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * date: 2021/8/18 16:59
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@TableName("student_course")
public class StudentCourse {
    @TableId(value = "id")
    private int id;
    private String studentUuid;
    private String courseUuid;

    public StudentCourse(String studentUuid, String courseUuid) {
        this.studentUuid = studentUuid;
        this.courseUuid = courseUuid;
    }
}
