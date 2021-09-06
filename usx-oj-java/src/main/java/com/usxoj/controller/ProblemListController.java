package com.usxoj.controller;

import cn.hutool.core.util.IdUtil;
import com.usxoj.entity.ProblemList;
import com.usxoj.entity.ProblemListCourse;
import com.usxoj.service.ProblemListCourseService;
import com.usxoj.service.ProblemListService;
import com.usxoj.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * date: 2021/8/19 15:28
 *
 * @author vinside
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/proList")
public class ProblemListController {

    @Autowired
    private ProblemListService problemListService;

    @Autowired
    private ProblemListCourseService problemListCourseService;

    //查询该课程下的所有题集
    @GetMapping
    public Result<?> query(@RequestParam String search,@RequestParam String courseUuid){
        List<ProblemList> problemLists = problemListService.findProblemListByCourse(courseUuid, search);
        int size = problemLists.size();
        for (int i = 0; i < size; i++) {
            problemLists.get(i).setProCount(problemListService.count(problemLists.get(i).getUuid()));
        }
        return Result.success(problemLists);
    }

    //添加题集,并关联课程
    @PostMapping
        public Result<?> save(@RequestBody Map<String,String> map) throws ParseException {
        ProblemList problemList = new ProblemList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//用于字符串转换为Date的格式
        System.out.println(map);
        problemList.setUuid(IdUtil.simpleUUID());
        problemList.setStartDate(sdf.parse(map.get("startDate")));
        problemList.setEndDate(sdf.parse(map.get("endDate")) );
        problemList.setAuthor(map.get("author"));
        problemList.setName(map.get("name"));
        problemListService.save(problemList);//保存数据至problem_list表

        ProblemListCourse problemListCourse = new ProblemListCourse(problemList.getUuid(),map.get("courseUuid"));
        problemListCourseService.save(problemListCourse);//保存数据至problemlist_course表
        return Result.success();
    }

    //根据UUID查询该题集
    @GetMapping("/getOne/{uuid}")
    public Result<?> getOne(@PathVariable String uuid){
        return Result.success(problemListService.getById(uuid));
    }

    //根据uuid更新题集数据
    @PostMapping("/update")
    public Result<?> update(@RequestBody ProblemList problemList){
        problemListService.updateById(problemList);
        return Result.success();
    }
}
