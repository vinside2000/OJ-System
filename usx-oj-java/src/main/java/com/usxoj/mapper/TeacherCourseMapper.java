package com.usxoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usxoj.entity.TeacherCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Description:
 * date: 2021/8/17 16:35
 *
 * @author vinside
 * @since JDK 1.8
 */
@Repository
public interface TeacherCourseMapper extends BaseMapper<TeacherCourse> {
}
