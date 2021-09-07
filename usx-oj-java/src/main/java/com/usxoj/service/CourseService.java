package com.usxoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.usxoj.entity.Course;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * date: 2021/8/13 20:47
 *
 * @author vinside
 * @since JDK 1.8
 */
@Service
public interface CourseService extends IService<Course> {

    //根据教师用户ID，查询该账户下的所有课程
    List<Course> findCourseByTeacher(String teacherUuid);

    //根据学生学号，查询该账户下的所有课程
    List<Course> findCourseByStudent(String studentNumber);

    //根据教师用户ID，查询未绑定在该教师账户下的所有课程
    List<Course> findAllCourseWithoutTeacher(String teacherUuid);
}
