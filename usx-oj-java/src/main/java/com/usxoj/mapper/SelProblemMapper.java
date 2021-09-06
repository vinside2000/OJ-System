package com.usxoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usxoj.entity.Problem;
import com.usxoj.entity.ProblemProblemList;
import com.usxoj.entity.SelProblem;
import com.usxoj.entity.SelproblemProblemlist;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Description:
 * date: 2021/8/25 10:36
 *
 * @author vinside
 * @since JDK 1.8
 */
@Repository
public interface SelProblemMapper extends BaseMapper<SelProblem> {

    //分页条件查询该题集下的所有题目
    IPage<Problem> findProblemByProblemList(Page<?> page,@Param("courseUuid") String courseUuid, @Param("problemListUuid") String problemListUuid, @Param("search") String search);

    //根据selProblemId删除selproblem_problemlist表里的数据，即从该题集中删除该题目
    void delete(@Param("selProblemUuid") String  selProblemUuid,@Param("problemListUuid") String problemListUuid);

    //将题目添加到对应的题集中
    void insertProTOList(@Param("selProblemUuid") String selProblemUuid,@Param("problemListUuid") String problemListUuid);

    //根据problemUuid和problemListUuid查询在中间表中是否已经存在，即该题目是否已经在该题集中
    SelproblemProblemlist isExist(@Param("selProblemUuid") String selProblemUuid, @Param("problemListUuid") String problemListUuid);
}
