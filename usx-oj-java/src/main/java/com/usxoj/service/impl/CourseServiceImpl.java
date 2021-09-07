package com.usxoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usxoj.entity.Course;
import com.usxoj.mapper.CourseMapper;
import com.usxoj.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findCourseByTeacher(String teacherUuid) {
        return courseMapper.findCourseByTeacher(teacherUuid);
    }

    @Override
    public List<Course> findCourseByStudent(String studentNumber) {
        return courseMapper.findCourseByStudent(studentNumber);
    }

    @Override
    public List<Course> findAllCourseWithoutTeacher(String teacherUuid) {
        return courseMapper.findAllCourseWithoutTeacher(teacherUuid);
    }
}
