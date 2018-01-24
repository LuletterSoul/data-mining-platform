package com.vero.dm.repository;

import com.vero.dm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  16:17 2018/1/24.
 * @since data-mining-platform
 */

public interface UserJpaRepository extends JpaRepository<User,String> {

}
