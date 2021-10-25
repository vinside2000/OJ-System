package com.usxoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usxoj.entity.StudentSelAnswer;
import com.usxoj.mapper.StudentSelAnswerMapper;
import com.usxoj.service.StudentSelAnswerService;
import org.springframework.stereotype.Service;

@Service
public class StudentSelAnswerServiceImpl extends ServiceImpl<StudentSelAnswerMapper, StudentSelAnswer> implements StudentSelAnswerService {
}
