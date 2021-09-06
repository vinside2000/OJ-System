package com.usxoj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.usxoj.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Description:
 * date: 2021/8/10 15:55
 *
 * @author vinside
 * @since JDK 1.8
 */
@Service
public interface StudentService extends IService<Student> {

    //从student_excel表复制数据到student表
    void copy();

    //分页查询该课程下的所有学生
    IPage<Student> findStudentByCourseByPage(Page<?> page, String courseUuid, String search);

}
