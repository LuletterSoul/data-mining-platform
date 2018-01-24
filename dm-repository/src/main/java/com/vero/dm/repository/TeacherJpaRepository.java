package com.vero.dm.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.vero.dm.model.Teacher;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:58 2018/1/24.
 * @since data-mining-platform
 */

public interface TeacherJpaRepository extends JpaRepository<Teacher, String>
{}
