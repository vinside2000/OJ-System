package com.usxoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usxoj.entity.Course;
import com.usxoj.entity.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * date: 2021/8/13 8:33
 *
 * @author vinside
 * @since JDK 1.8
 */
@Repository
public interface TeacherMapper extends BaseMapper<Teacher> {

    List<Course> findCourseFormTeacher(String teacherUuid);
}
