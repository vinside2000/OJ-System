package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("student_sel_answer")
public class StudentSelAnswer {
    @TableId(value = "id")
    private int id;
    private String problemListUuid;
    private String studentUuid;
    private int selproblemId;
    private String selproblemAnswer;
    private int mark;
    private int status;

    public StudentSelAnswer(String problemListUuid, String studentUuid, int selproblemId, String selproblemAnswer) {
        this.problemListUuid = problemListUuid;
        this.studentUuid = studentUuid;
        this.selproblemId = selproblemId;
        this.selproblemAnswer = selproblemAnswer;
    }
}
