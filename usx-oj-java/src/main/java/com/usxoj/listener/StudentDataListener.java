package com.usxoj.listener;

import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.usxoj.entity.Student;
import com.usxoj.entity.StudentExcel;
import com.usxoj.service.StudentExcelService;
import com.usxoj.service.StudentService;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * date: 2021/8/10 15:37
 *
 * @author vinside
 * @since JDK 1.8
 */
public class StudentDataListener extends AnalysisEventListener<StudentExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;
    List<StudentExcel> list = new ArrayList<StudentExcel>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private StudentExcelService studentExcelService;
    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param studentExcelService
     */
    public StudentDataListener(StudentExcelService studentExcelService) {
        this.studentExcelService = studentExcelService;
    }


    @Override
    public void invoke(StudentExcel data, AnalysisContext analysisContext) {
        data.setUuid(IdUtil.fastSimpleUUID());
        data.setUsername(data.getNumber());
        data.setPassword(data.getNumber());
        list.add(data);
        System.out.println(list);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        studentExcelService.saveBatch(list);
    }
}
