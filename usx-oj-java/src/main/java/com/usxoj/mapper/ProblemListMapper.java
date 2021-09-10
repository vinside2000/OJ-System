package com.usxoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usxoj.entity.ProblemList;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * date: 2021/8/19 15:37
 *
 * @author vinside
 * @since JDK 1.8
 */
@Repository
public interface ProblemListMapper extends BaseMapper<ProblemList> {

    //查询该课程下所有题集
    List<ProblemList> findProblemListByCourse(String courseUuid,String search);

    //查询该题集下的程序题数量
    int count(String problemListUuid);

    //查询该题集下的选择题数量
    int selCount(String problemListUuid);
}
