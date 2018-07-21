package com.vero.dm.service;


import com.vero.dm.model.Teacher;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:07 2018/7/21.
 * @since data-mining-platform
 */

public interface TeacherService extends UserService
{
    Teacher findTeacherById(String id);
}
