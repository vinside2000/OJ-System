package com.usxoj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usxoj.entity.Student;
import com.usxoj.mapper.StudentMapper;
import com.usxoj.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Description:
 * date: 2021/8/10 15:56
 *
 * @author vinside
 * @since JDK 1.8
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public void copy() {
        studentMapper.copy();
    }

    @Override
    public IPage<Student> findStudentByCourseByPage(Page<?> page, String courseUuid, String search) {
        return studentMapper.findStudentByCourseByPage(page,courseUuid,search);
    }


}
