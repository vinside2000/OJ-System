package com.usxoj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usxoj.entity.Problem;
import com.usxoj.entity.ProblemAnswer;
import com.usxoj.entity.ProblemProblemList;
import com.usxoj.mapper.ProblemMapper;
import com.usxoj.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * date: 2021/8/20 21:14
 *
 * @author vinside
 * @since JDK 1.8
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {

    @Autowired
    private ProblemMapper problemMapper;

    @Override
    public IPage<Problem> findProblemByProblemList(Page<?> page,String courseUuid, String problemListUuid, String search) {
        return problemMapper.findProblemByProblemList(page,courseUuid,problemListUuid,search);
    }

    @Override
    public void delete(String problemUuid, String problemListUuid) {
        problemMapper.delete(problemUuid,problemListUuid);
    }

    @Override
    public void insertProTOList(String problemUuid, String problemListUuid) {
        problemMapper.insertProTOList(problemUuid,problemListUuid);
    }

    @Override
    public ProblemProblemList isExist(String problemUuid, String problemListUuid) {
        return problemMapper.isExist(problemUuid,problemListUuid);
    }

    @Override
    public void saveAnswer(ProblemAnswer problemAnswer) {
        problemMapper.saveAnswer(problemAnswer);
    }

    @Override
    public ProblemAnswer findAnswerByUuid(String problemUuid) {
        return problemMapper.findAnswerByUuid(problemUuid);
    }

    @Override
    public void updateText(ProblemAnswer problemAnswer) {
        problemMapper.updateText(problemAnswer);
    }
}
