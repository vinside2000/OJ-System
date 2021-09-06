package com.usxoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usxoj.entity.StudentExcel;
import org.springframework.stereotype.Repository;

/**
 * Description:
 * date: 2021/8/18 18:56
 *
 * @author vinside
 * @since JDK 1.8
 */
@Repository
public interface StudentExcelMapper extends BaseMapper<StudentExcel> {

    //清除student_excel表中数据
    void clear();
}
