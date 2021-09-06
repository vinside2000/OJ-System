package com.usxoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usxoj.entity.StudentExcel;
import com.usxoj.mapper.StudentExcelMapper;
import com.usxoj.service.StudentExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * date: 2021/8/18 18:56
 *
 * @author vinside
 * @since JDK 1.8
 */
@Service
public class StudentExcelServiceImpl extends ServiceImpl<StudentExcelMapper, StudentExcel> implements StudentExcelService {

    @Autowired
    private StudentExcelMapper studentExcelMapper;

    @Override
    public void clear() {
        studentExcelMapper.clear();
    }
}
