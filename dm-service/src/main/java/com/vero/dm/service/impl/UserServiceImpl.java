package com.vero.dm.service.impl;


import java.util.List;

import net.sf.ehcache.Ehcache;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.vero.dm.model.Role;
import com.vero.dm.model.User;
import com.vero.dm.repository.dto.UserDto;
import com.vero.dm.service.UserService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:14 2017/7/14.
 * @description
 * @modified by:
 */
@Service
@Slf4j
public class UserServiceImpl extends AbstractBaseServiceImpl<User, String> implements UserService
{

    @Override
    public User findById(String id)
    {
        return userJpaRepository.findOne(id);
    }


    @Override
    public UserDto getUserProfile(String username)
    {
        User user = userJpaRepository.findUserByUsername(username);
        UserDto userDTO = new UserDto();
        BeanUtils.copyProperties(user, userDTO);
        if (user.getAvatar() != null)
        {
            userDTO.setAvatar(new String(user.getAvatar()));
        }
        return userDTO;
    }

    @Override
    public UserDto updateUser(UserDto userDto)
    {
        User user = this.findById(userDto.getUserId());
        BeanUtils.copyProperties(userDto, user);
        user.setAvatar(userDto.getAvatar().getBytes());
        userJpaRepository.save(user);
        return userDto;
    }
    //
    // public User doUserCredentialsMatch(User user, DisposableSaltEntry entry)
    // {
    // Subject subject = SecurityUtils.getSubject();
    // String disposableSalt = loginDisposableSaltCache.get(entry.getTmpId()).toHex();
    // if (disposableSalt == null)
    // {
    // throw new InternalAuthenticationException(
    // "The disposable salt can't be retrieve in current cache.");
    // }
    // /*
    // * 及时清除缓存;
    // */
    // loginDisposableSaltCache.remove(entry.getTmpId());
    // // 解密
    // String submittedPlait = EncryptUtils.aesDecrypt(user.getPassword(), disposableSalt);
    // UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),
    // submittedPlait);
    // subject.login(token);
    // return userDao.findByUserName(user.getUserName());
    // }

    // @Override
    // public DisposableSaltEntry getRandomVerifySaltEntry(String preSaltId)
    // {
    // /*
    // * 清除上一次缓存的校验随机盐;
    // */
    // LOGGER.info("Receive pre salt id [{}] at front end.", preSaltId);
    // if (preSaltId != null && !preSaltId.equals("\"\""))
    // {
    // loginDisposableSaltCache.remove(preSaltId);
    // LOGGER.info("Remove pre-salt from cache.");
    // }
    // ByteSource randomSalt = passwordService.generateRandomSalt(8);
    // String tmpId = UUID.randomUUID().toString();
    // /*
    // * 重新生成校验随机盐到前台
    // */
    // loginDisposableSaltCache.put(tmpId, randomSalt);
    // LOGGER.info("Put disposable salt entry to cache.Id:[{}],Salt:[{}].", tmpId,
    // randomSalt.toHex());
    // return new DisposableSaltEntry(tmpId, randomSalt.toHex());
    // }

    public void correlateRoles(String userId, List<Long> roleIdList)
    {
        // User user = userDao.fetchUserJoinRolesById(userId);
        // for (Long roleId : roleIdList)
        // {
        // user.getRoles().add(roleDao.findById(roleId));
        // }
        // userDao.update(user);
    }

    // public void removeRoles(String userId, List<Long> roleIdList)
    // {
    // try {
    // User user = userDao.findById(userId);
    // for (Long roleId :
    // roleIdList)
    // {
    // user.getRoleSet().remove(roleDao.findById(roleId));
    // }
    // userDao.update(user);
    // } catch (DataObjectNotFoundException e)
    // {
    // e.printStackTrace();
    // }
    // }
    //
    // public void removeRole(String userId, Long roleId)
    // {
    // List<Long> roleIdList = new ArrayList<Long>();
    // roleIdList.add(roleId);
    // removeRoles(userId,roleIdList);
    // }

    @Override
    public User fetchByUserName(String username)
    {
        return userJpaRepository.findUserByUsername(username);
    }

    // public List<Permission> findPermissionByUserName(String userName)
    // {
    // // return userDao.findPermissionByUserName(userName);
    // return null;
    // }

    public List<Role> findRolesByUserName(String userName)
    {
        return userJpaRepository.findRolesByUsername(userName);
    }

    public List<String> findRoleNameSetByUserName(String username)
    {
        return userJpaRepository.findRoleNameSetByUsername(username);
    }

    @Override
    public List<String> findPermissionNameSet(String username)
    {
        return userJpaRepository.findPermissionNameSet(username);
    }

    public void removeRoles(String userId, List<Long> roleIdList)
    {
        // List<Role> roleList = roleDao.fetchRoleListByIdList(roleIdList);
        // userDao.removeRoles(userId, roleIdList);
    }

    @Override
    public String fetchPublicSalt(String username)
    {
        return userJpaRepository.findPublicSaltByUsername(username);
    }

    @Cacheable(cacheNames = "userPrivateSaltCache")
    @Override
    public String fetchPrivateSalt(String username)
    {
        return userJpaRepository.findPrivateSaltByUsername(username);
    }

}
