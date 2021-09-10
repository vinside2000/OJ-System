package com.usxoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.usxoj.entity.ProblemList;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * date: 2021/8/19 15:35
 *
 * @author vinside
 * @since JDK 1.8
 */
@Service
public interface ProblemListService extends IService<ProblemList> {

    //查询该课程下所有题集
    List<ProblemList> findProblemListByCourse(String courseUuid,String search);

    //查询该题集下的程序题数量
    int count(String problemListUuid);

    //查询该题集下的选择题数量
    int selCount(String problemListUuid);
}
