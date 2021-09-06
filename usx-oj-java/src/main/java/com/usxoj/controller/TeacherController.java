package com.usxoj.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.usxoj.entity.Teacher;
import com.usxoj.service.TeacherService;
import com.usxoj.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * date: 2021/8/13 8:38
 *
 * @author vinside
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/tea")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Teacher teacher){
        Teacher res = teacherService.getOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getUsername,teacher.getUsername()).eq(Teacher::getPassword,teacher.getPassword()));
        if (res == null){
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success(res);
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Teacher teacher){
        teacher.setUuid(IdUtil.simpleUUID());
        teacher.setUsername(teacher.getJobNumber());
        teacher.setPassword(teacher.getJobNumber());
        teacherService.save(teacher);
        return Result.success();
    }

    @GetMapping("/course/{uuid}")
    public Result<?> findCourseFormTeacher(@PathVariable String uuid){
        return Result.success(teacherService.findCourseFormTeacher(uuid));
    }
}
