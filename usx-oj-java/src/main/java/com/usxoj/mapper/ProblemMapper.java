package com.usxoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usxoj.entity.Problem;
import com.usxoj.entity.ProblemAnswer;
import com.usxoj.entity.ProblemProblemList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * date: 2021/8/20 20:42
 *
 * @author vinside
 * @since JDK 1.8
 */
@Repository
public interface ProblemMapper extends BaseMapper<Problem> {

    //分页条件查询该题集下的所有题目
    IPage<Problem> findProblemByProblemList(Page<?> page,@Param("courseUuid") String courseUuid, @Param("problemListUuid") String problemListUuid, @Param("search") String search);

    //根据problemUuid删除problem_problemlist表里的数据，即从该题集中删除该题目
    void delete(@Param("problemUuid") String problemUuid,@Param("problemListUuid") String problemListUuid);

    //将题目添加到对应的题集中
    void insertProTOList(@Param("problemUuid") String problemUuid,@Param("problemListUuid") String problemListUuid);

    //根据problemUuid和problemListUuid查询在中间表中是否已经存在，即该题目是否已经在该题集中
    ProblemProblemList isExist(@Param("problemUuid") String problemUuid,@Param("problemListUuid") String problemListUuid);

    //将测试用例添加到答案表
    void saveAnswer(ProblemAnswer problemAnswer);

    //根据uuid查询题目以及测试用例
    ProblemAnswer findAnswerByUuid(String problemUuid);

    //根据id修改problemAnswer测试用例数据
    void updateText(ProblemAnswer problemAnswer);
}
