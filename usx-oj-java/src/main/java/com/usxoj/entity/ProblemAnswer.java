package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * date: 2021/8/28 14:40
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@TableName("problem_answer")
public class ProblemAnswer {
    @TableId(value = "id")
    private int id;
    private String problemUuid;
    private String inputA;
    private String outputA;
    private String inputB;
    private String outputB;
    private String inputC;
    private String outputC;
    private String inputD;
    private String outputD;
    private String inputE;
    private String outputE;

    public ProblemAnswer(String problemUuid, String inputA, String outputA, String inputB, String outputB, String inputC, String outputC, String inputD, String outputD, String inputE, String outputE) {
        this.problemUuid = problemUuid;
        this.inputA = inputA;
        this.outputA = outputA;
        this.inputB = inputB;
        this.outputB = outputB;
        this.inputC = inputC;
        this.outputC = outputC;
        this.inputD = inputD;
        this.outputD = outputD;
        this.inputE = inputE;
        this.outputE = outputE;
    }
}
