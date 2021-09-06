package com.usxoj.controller;

import com.usxoj.entity.TeacherCourse;
import com.usxoj.service.TeacherCourseService;
import com.usxoj.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Description:
 * date: 2021/8/18 14:34
 *
 * @author vinside
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/tc")
public class TeacherCourseController {

    @Autowired
    private TeacherCourseService teacherCourseService;

    @PostMapping
    public Result<?> save(@RequestBody Map<String ,String > map){
        TeacherCourse teacherCourse = new TeacherCourse(map.get("teacherUuid"),map.get("courseUuid"));
        teacherCourseService.save(teacherCourse);
        return Result.success();
    }
}
