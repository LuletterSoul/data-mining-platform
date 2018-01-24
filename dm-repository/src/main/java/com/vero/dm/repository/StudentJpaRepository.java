package com.vero.dm.repository;

import com.vero.dm.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  20:18 2018/1/24.
 * @since data-mining-platform
 */

public interface  StudentJpaRepository extends JpaRepository<Student,String>{
}
