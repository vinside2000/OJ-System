package com.usxoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.usxoj.entity.Course;
import com.usxoj.entity.Teacher;
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
public interface TeacherService extends IService<Teacher> {
    List<Course> findCourseFormTeacher(String teacherUuid);
}
