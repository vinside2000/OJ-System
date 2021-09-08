package com.usxoj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usxoj.entity.Problem;
import com.usxoj.entity.SelProblem;
import com.usxoj.entity.SelproblemProblemlist;
import com.usxoj.mapper.SelProblemMapper;
import com.usxoj.service.SelProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * date: 2021/8/25 10:37
 *
 * @author vinside
 * @since JDK 1.8
 */
@Service
public class SelProblemServiceImpl extends ServiceImpl<SelProblemMapper, SelProblem> implements SelProblemService {

    @Autowired
    private SelProblemMapper selProblemMapper;

    @Override
    public IPage<SelProblem> findProblemByProblemList(Page<?> page,String courseUuid, String problemListUuid, String search) {
        return selProblemMapper.findProblemByProblemList(page,courseUuid,problemListUuid,search);
    }

    @Override
    public void delete(String  selProblemUuid, String problemListUuid) {
        selProblemMapper.delete(selProblemUuid,problemListUuid);
    }

    @Override
    public void insertProTOList(String  selProblemUuid, String problemListUuid) {
        selProblemMapper.insertProTOList(selProblemUuid,problemListUuid);
    }

    @Override
    public SelproblemProblemlist isExist(String selProblemUuid, String problemListUuid) {
        return selProblemMapper.isExist(selProblemUuid,problemListUuid);
    }

    @Override
    public List<SelProblem> findSelProDetailByProblemList(String problemListUuid) {
        return selProblemMapper.findSelProDetailByProblemList(problemListUuid);
    }
}
