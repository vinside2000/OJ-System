package com.usxoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usxoj.entity.ProblemList;
import com.usxoj.mapper.ProblemListMapper;
import com.usxoj.service.ProblemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * date: 2021/8/19 15:36
 *
 * @author vinside
 * @since JDK 1.8
 */
@Service
public class ProblemListServiceImpl extends ServiceImpl<ProblemListMapper, ProblemList> implements ProblemListService {

    @Autowired
    private ProblemListMapper problemListMapper;

    @Override
    public List<ProblemList> findProblemListByCourse(String courseUuid,String search) {
        return problemListMapper.findProblemListByCourse(courseUuid,search);
    }

    @Override
    public int count(String problemListUuid) {
        return problemListMapper.count(problemListUuid);
    }

    @Override
    public int selCount(String problemListUuid) {
        return problemListMapper.selCount(problemListUuid);
    }
}
