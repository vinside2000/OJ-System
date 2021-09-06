package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Description:
 * date: 2021/8/26 14:29
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
@TableName("selproblem_problemlist")
public class SelproblemProblemlist {
    @TableId(value = "id")
    private int id;
    private String selproblemUuid;
    private String problemListUuid;

}
