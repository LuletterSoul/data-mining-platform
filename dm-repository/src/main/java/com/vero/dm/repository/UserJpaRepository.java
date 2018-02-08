package com.vero.dm.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vero.dm.model.Role;
import com.vero.dm.model.User;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:17 2018/1/24.
 * @since data-mining-platform
 */

public interface UserJpaRepository extends JpaRepository<User, String>
{
    User findUserByUsername(String username);

    @Query(value = "select u.publicSalt from User u where u.username = :username")
    String findPublicSaltByUsername(@Param("username") String username);

    @Query(value = "select u.privateSalt from User u where u.username = :username")
    String findPrivateSaltByUsername(@Param("username") String username);

    @Query(value = "select r.roleName from User u left join u.roles r where u.username =:username")
    List<String> findRoleNameSetByUsername(@Param("username") String username);

    @Query(value = "select r from User u left join u.roles r where u.username = :username")
    List<Role> findRolesByUsername(@Param("username")String username);

    @Query(value = "select p.permissionName from User u left join u.roles r left join r.permissionSet p where u.username =:username")
    List<String> findPermissionNameSet(@Param("username")String username);
}
