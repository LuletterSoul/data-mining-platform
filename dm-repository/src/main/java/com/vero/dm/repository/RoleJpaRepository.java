package com.vero.dm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vero.dm.model.Role;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:40 2018/1/24.
 * @since data-mining-platform
 */

public interface RoleJpaRepository extends JpaRepository<Role, Long>
{
    Role findAllByRoleName(String roleName);

    @Query(value = "select r from Role r where r.roleName in :roleNames")
    List<Role> findAllByRoleNames(@Param("roleNames") List<String> roleNames);
}
