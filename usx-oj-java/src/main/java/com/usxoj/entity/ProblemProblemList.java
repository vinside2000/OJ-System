package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Description:
 * date: 2021/8/26 13:55
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
@TableName("problem_problemlist")
public class ProblemProblemList {
    @TableId(value = "id")
    private int id;
    private String problemUuid;
    private String problemListUuid;
}
