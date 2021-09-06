package com.usxoj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 * date: 2021/8/10 15:05
 *
 * @author vinside
 * @since JDK 1.8
 */
@Data
public class StudentExcelModel extends BaseRowModel implements Serializable {

    @ExcelProperty(value = "学校",index = 0)
    private String school;

    @ExcelProperty(value = "专业",index = 1)
    private String major;

    @ExcelProperty(value = "班级",index = 2)
    private String studentClass;

    @ExcelProperty(value = "学号",index = 3)
    private String number;

    @ExcelProperty(value = "姓名",index = 4)
    private String name;

    @ExcelProperty(value = "性别",index = 5)
    private String gender;
}
