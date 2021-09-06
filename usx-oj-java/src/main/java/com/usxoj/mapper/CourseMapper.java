package com.usxoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usxoj.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * date: 2021/8/13 20:48
 *
 * @author vinside
 * @since JDK 1.8
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {

    //根据教师用户ID，查询该账户下的所有课程
    List<Course> findCourseByTeacher(String teacherUuid);

    //根据教师用户ID，查询未绑定在该教师账户下的所有课程
    List<Course> findAllCourseWithoutTeacher(String teacherUuid);
}
