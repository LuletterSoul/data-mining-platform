package com.vero.dm.repository;

import com.vero.dm.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  16:40 2018/1/24.
 * @since data-mining-platform
 */


public interface RoleJpaRepository extends JpaRepository<Role,Long> {

}
