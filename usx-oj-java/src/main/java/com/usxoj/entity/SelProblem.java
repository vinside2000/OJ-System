package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * date: 2021/8/25 10:24
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@TableName("selproblem")
public class SelProblem {
    @TableId(value = "id")
    private int id;
    private String title;//题目内容
    private String type;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int mark;
    private String answer;
    private String uuid;

    public SelProblem(String title, String type, String optionA, String optionB, String optionC, String optionD, int mark, String answer, String uuid) {
        this.title = title;
        this.type = type;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.mark = mark;
        this.answer = answer;
        this.uuid = uuid;
    }
}
