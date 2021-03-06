package com.usxoj.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usxoj.entity.*;
import com.usxoj.service.SelProblemService;
import com.usxoj.service.StudentSelAnswerService;
import com.usxoj.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * date: 2021/8/25 10:38
 *
 * @author vinside
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/selPro")
public class SelProblemController {

    @Autowired
    private SelProblemService selProblemService;

    @Autowired
    private StudentSelAnswerService studentSelAnswerService;

    //根据题集分页条件查询
    @GetMapping
    public Result<?> findSelProblemByProblemListInPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                                    @RequestParam(defaultValue = "") String search,
                                                    @RequestParam(defaultValue = "") String problemListUuid,
                                                    @RequestParam String courseUuid){
        return Result.success(selProblemService.findProblemByProblemList(new Page<Integer>(pageNum,pageSize),courseUuid,problemListUuid,search));
    }

    //根据problemListUuid查询所有选择题
    @GetMapping("/getSel/{problemListUuid}")
    public Result<?> findSelProDetailByProblemList(@PathVariable String problemListUuid){
            return Result.success(selProblemService.findSelProDetailByProblemList(problemListUuid));
    }

    //将题目添加到对应的题集中
    @PostMapping
    public Result<?> save(@RequestBody Map<String ,String> map){
        SelProblem selProblem = new SelProblem( map.get("title"),
                                                map.get("type"),
                                                map.get("optionA"),
                                                map.get("optionB"),
                                                map.get("optionC"),
                                                map.get("optionD"),
                                                Integer.parseInt(map.get("mark")),
                                                map.get("answer"),
                                                IdUtil.simpleUUID());
        selProblemService.save(selProblem);
        selProblemService.insertProTOList(selProblem.getUuid(),map.get("problemListUuid"));
        return Result.success();
    }

    //根据selProblemId删除selproblem_problemlist表里的数据，即从该题集中删除该题目
    @PostMapping("/del")
    public Result<?> delete(@RequestBody Map<String ,String> map){
        if (!map.get("problemListUuid").isEmpty()){
            selProblemService.delete(map.get("selProblemUuid"),map.get("problemListUuid"));
            return Result.success();
        }
        return Result.error("-1","删除失败，只能删除特定题集下的题目");
    }

    //查询所有
    @GetMapping("/all")
    public Result<?> queryAllPro(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "") String search){
        return Result.success(selProblemService.page(new Page<>(pageNum,pageSize), Wrappers.<SelProblem>lambdaQuery().like(SelProblem::getTitle,search)));
    }

    //将现有题目添加到题集中，只需要将两者UUID关联起来
    @PostMapping("/add")
    public Result<?> add(@RequestBody Map<String, String> map){
        String  selProblemUuid = map.get("selProblemUuid");
        String  problemListUuid = map.get("problemListUuid");

        SelproblemProblemlist item = selProblemService.isExist(selProblemUuid, problemListUuid);

        if ("".equals(problemListUuid)){//当题集未选择，题集UUID为空时
            return Result.error("-1","添加失败，请先选择题集");
        }else if (item != null){
            return Result.error("-1","添加失败，该题集中已经存在该题目");
        }
        selProblemService.insertProTOList(selProblemUuid,problemListUuid);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getOne(@PathVariable Integer id){
        return Result.success(selProblemService.getById(id));
    }

    //根据id修改数据
    @PostMapping("/update")
    public Result<?> updateById(@RequestBody SelProblem selProblem){
        selProblemService.updateById(selProblem);
        return Result.success();
    }

    //将个人答题情况保存到数据库
    @PostMapping("/answer")
    public Result<?> saveAnswer(@RequestBody Map<String ,String> map){
//        System.out.println(map);
        String studentUuid = map.get("studentUuid");
        String proListUuid = map.get("proListUuid");
        String answerMap = map.get("answerMap");
        Map<String,String> m= (Map<String, String>) JSON.parse(answerMap);
//        System.out.println(m);
        List<StudentSelAnswer> list = new ArrayList<>();
        StudentSelAnswer studentSelAnswer = null;
        for (Map.Entry<String,String> entry:m.entrySet()){
            studentSelAnswer = new StudentSelAnswer(proListUuid,studentUuid,Integer.parseInt(entry.getKey()),entry.getValue());
            SelProblem selProblem = selProblemService.getById(entry.getKey());
            if (selProblem.getAnswer().equals(entry.getValue())){
                studentSelAnswer.setMark(5);
                studentSelAnswer.setStatus(1);
            }else {
                studentSelAnswer.setMark(0);
                studentSelAnswer.setStatus(0);
            }
            list.add(studentSelAnswer);
        }
        for (StudentSelAnswer selAnswer : list) {
            studentSelAnswerService.saveOrUpdate(selAnswer,
                    Wrappers.<StudentSelAnswer>lambdaQuery()
                            .eq(StudentSelAnswer::getStudentUuid,studentUuid)
                            .eq(StudentSelAnswer::getProblemListUuid,proListUuid)
                            .eq(StudentSelAnswer::getSelproblemId,selAnswer.getSelproblemId()));
        }
        return Result.success();
    }

}
