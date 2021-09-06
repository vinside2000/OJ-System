package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * date: 2021/8/19 16:07
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@TableName("problemlist_course")
public class ProblemListCourse {
    @TableId(value = "id")
    private int id;
    private String problemListUuid;
    private String courseUuid;

    public ProblemListCourse(String problemListUuid, String courseUuid) {
        this.problemListUuid = problemListUuid;
        this.courseUuid = courseUuid;
    }
}
