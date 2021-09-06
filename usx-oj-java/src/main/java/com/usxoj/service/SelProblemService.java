package com.usxoj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.usxoj.entity.Problem;
import com.usxoj.entity.SelProblem;
import com.usxoj.entity.SelproblemProblemlist;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * Description:
 * date: 2021/8/25 10:37
 *
 * @author vinside
 * @since JDK 1.8
 */
@Service
public interface SelProblemService extends IService<SelProblem> {

    //分页条件查询该题集下的所有题目
    IPage<Problem> findProblemByProblemList(Page<?> page, String courseUuid, String problemListUuid, String search);

    //根据selProblemId删除selproblem_problemlist表里的数据，即从该题集中删除该题目
    void delete(String  selProblemUuid, String problemListUuid);

    //将题目添加到对应的题集中
    void insertProTOList(String  selProblemUuid, String problemListUuid);

    //根据problemUuid和problemListUuid查询在中间表中是否已经存在，即该题目是否已经在该题集中
    SelproblemProblemlist isExist(String selProblemUuid, String problemListUuid);
}
