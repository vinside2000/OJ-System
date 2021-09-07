package com.usxoj.controller;

import cn.hutool.core.util.IdUtil;
import com.usxoj.entity.Course;
import com.usxoj.entity.TeacherCourse;
import com.usxoj.service.CourseService;
import com.usxoj.service.TeacherCourseService;
import com.usxoj.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.Map;

/**
 * Description:
 * date: 2021/8/13 20:49
 *
 * @author vinside
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    @PostMapping("/save")
    public Result<?> save(@RequestBody Map<String,String> map){
        System.out.println(map);
        Course course = new Course(IdUtil.simpleUUID(),map.get("courseName"),map.get("courseImg"),new Date());
        TeacherCourse teacherCourse = new TeacherCourse(map.get("uuid"),course.getCourseUuid());
        courseService.save(course);//添加一条课程数据
        teacherCourseService.save(teacherCourse);//添加教师-课程中间表数据
        return Result.success();
    }

    //根据教师用户ID，查询该账户下的所有课程
    @GetMapping("/{uuid}")
    public Result<?> findCourseByTeacher(@PathVariable String uuid){
        return Result.success(courseService.findCourseByTeacher(uuid));
    }

    //根据教师用户ID，查询未绑定在该教师账户下的所有课程
    @GetMapping("/all/{uuid}")
    public Result<?> findAllCourseWithoutTeacher(@PathVariable String uuid){
        return Result.success(courseService.findAllCourseWithoutTeacher(uuid));
    }

    //根据教师用户ID，查询该账户下的所有课程
    @GetMapping("/stu/{number}")
    public Result<?> findCourseByStudent(@PathVariable String number){
        return Result.success(courseService.findCourseByStudent(number));
    }
}
