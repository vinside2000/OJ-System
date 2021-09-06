package com.usxoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usxoj.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * date: 2021/8/9 15:30
 *
 * @author vinside
 * @since JDK 1.8
 */
@Repository
public interface StudentMapper extends BaseMapper<Student> {

    //从student_excel表复制数据到student表
    boolean copy();

    //分页查询该课程下的所有学生
    IPage<Student> findStudentByCourseByPage(Page<?> page, @Param("courseUuid") String courseUuid, @Param("search") String search);
}
