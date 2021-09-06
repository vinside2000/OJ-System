package com.usxoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usxoj.entity.StudentCourse;
import com.usxoj.mapper.StudentCourseMapper;
import com.usxoj.service.StudentCourseService;
import org.springframework.stereotype.Service;

/**
 * Description:
 * date: 2021/8/18 17:06
 *
 * @author vinside
 * @since JDK 1.8
 */
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {
}
