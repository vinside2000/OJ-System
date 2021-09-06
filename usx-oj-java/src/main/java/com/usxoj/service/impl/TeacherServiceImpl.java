package com.usxoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usxoj.entity.Course;
import com.usxoj.entity.Teacher;
import com.usxoj.mapper.TeacherMapper;
import com.usxoj.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * date: 2021/8/13 8:32
 *
 * @author vinside
 * @since JDK 1.8
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Course> findCourseFormTeacher(String teacherUuid) {
        return teacherMapper.findCourseFormTeacher(teacherUuid);
    }
}
