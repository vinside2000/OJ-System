package com.usxoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.usxoj.entity.StudentExcel;
import org.springframework.stereotype.Service;

/**
 * Description:
 * date: 2021/8/18 18:55
 *
 * @author vinside
 * @since JDK 1.8
 */
@Service
public interface StudentExcelService extends IService<StudentExcel> {

    void clear();
}
