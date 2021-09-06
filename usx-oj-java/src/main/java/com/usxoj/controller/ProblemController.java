package com.usxoj.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usxoj.entity.Problem;
import com.usxoj.entity.ProblemAnswer;
import com.usxoj.entity.ProblemProblemList;
import com.usxoj.entity.Student;
import com.usxoj.service.ProblemService;
import com.usxoj.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Description:
 * date: 2021/8/20 20:38
 *
 * @author vinside
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/pro")
public class ProblemController {

    @Autowired
    private ProblemService problemService;


    //根据题集查询
    @GetMapping
    public Result<?> findProblemByProblemListInpage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                                    @RequestParam(defaultValue = "") String search,
                                                    @RequestParam(defaultValue = "") String problemListUuid,
                                                    @RequestParam String courseUuid) {
        return Result.success(problemService.findProblemByProblemList(new Page<Integer>(pageNum, pageSize),courseUuid, problemListUuid, search));
    }

    //查询所有
    @GetMapping("/all")
    public Result<?> queryAllPro(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "") String search) {
        return Result.success(problemService.page(new Page<>(pageNum, pageSize), Wrappers.<Problem>lambdaQuery().like(Problem::getTitle, search)));
    }

    @PostMapping
    public Result<?> save(@RequestBody Map<String, String> map) {
        Problem problem = new Problem(map.get("title"), map.get("type"), map.get("content"), Integer.parseInt(map.get("mark")), IdUtil.simpleUUID());
        problemService.save(problem);
        problemService.saveAnswer(new ProblemAnswer(problem.getUuid(),
                                                    map.get("inputA"),
                                                    map.get("outputA"),
                                                    map.get("inputB"),
                                                    map.get("outputB"),
                                                    map.get("inputC"),
                                                    map.get("outputC"),
                                                    map.get("inputD"),
                                                    map.get("outputD"),
                                                    map.get("inputE"),
                                                    map.get("outputE")));
        problemService.insertProTOList(problem.getUuid(), map.get("problemListUuid"));
        return Result.success();
    }

    @PostMapping("/del")
    public Result<?> delete(@RequestBody Map<String, String> map) {
        if (!map.get("problemListUuid").isEmpty()) {
            problemService.delete(map.get("problemUuid"), map.get("problemListUuid"));
            return Result.success();
        }
        return Result.error("-1", "删除失败，只能删除特定题集下的题目");
    }

    //将现有题目添加到题集中，只需要将两者UUID关联起来
    @PostMapping("/add")
    public Result<?> add(@RequestBody Map<String, String> map){
        String  problemUuid = map.get("problemUuid");
        String  problemListUuid = map.get("problemListUuid");

        ProblemProblemList item = problemService.isExist(problemUuid, problemListUuid);

        if ("".equals(problemListUuid)){//当题集未选择，题集UUID为空时
            return Result.error("-1","添加失败，请先选择题集");
        }else if (item != null){
            return Result.error("-1","添加失败，该题集中已经存在该题目");
        }
        problemService.insertProTOList(problemUuid,problemListUuid);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getOne(@PathVariable Integer id){
        return Result.success(problemService.getById(id));
    }

    //根据id修改数据
    @PostMapping("/update")
    public Result<?> updateById(@RequestBody Problem problem){
        problemService.updateById(problem);
        return Result.success();
    }

    @GetMapping("/text/{uuid}")
    public Result<?> findAnswerByUuid(@PathVariable String uuid){
        return Result.success(problemService.findAnswerByUuid(uuid));
    }

    @PostMapping("/updateText")
    public Result<?> updateText(@RequestBody ProblemAnswer problemAnswer){
        problemService.updateText(problemAnswer);
        return Result.success();
    }
}
