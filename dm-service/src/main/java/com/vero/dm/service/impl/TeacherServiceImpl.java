package com.vero.dm.service.impl;

import com.vero.dm.model.Teacher;
import com.vero.dm.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  14:08 2018/7/21.
 * @since data-mining-platform
 */

@Slf4j
@Service
public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {


    @Override
    public Teacher findTeacherById(String id) {
        return teacherJpaRepository.findOne(id);
    }
}
