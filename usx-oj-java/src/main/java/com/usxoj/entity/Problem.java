package com.usxoj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description:
 * date: 2021/8/20 14:35
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@TableName("problem")
public class Problem {
    @TableId(value = "id")
    private int id;
    private String title;
    private String type;
    private String content;
    private int mark;
    private String uuid;

    public Problem(String title, String type, String content, int mark, String uuid) {
        this.title = title;
        this.type = type;
        this.content = content;
        this.mark = mark;
        this.uuid = uuid;
    }
}
