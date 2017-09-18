package com.dm.org.service.impl;


import com.dm.org.exceptions.DataObjectNotFoundException;
import com.dm.org.model.Permission;
import com.dm.org.model.Role;
import com.dm.org.model.User;
import com.dm.org.security.UserPasswordService;
import com.dm.org.security.credentials.DisposableSaltEntry;
import com.dm.org.security.credentials.EncryptUtils;
import com.dm.org.service.StatelessCredentialsService;
import com.dm.org.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:14 2017/7/14.
 * @description
 * @modified by:
 */
@Service
public class UserServiceImpl extends AbstractBaseServiceImpl<User, String> implements UserService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private StatelessCredentialsService credentialsService;

    private Cache<String, ByteSource> loginDisposableSaltCache;

    private UserPasswordService passwordService;

    @Autowired
    public void setEhCacheManager(EhCacheManager ehCacheManager)
    {
        loginDisposableSaltCache = ehCacheManager.getCache("loginDisposableSaltCache");
    }

    @Autowired
    public void setPasswordService(UserPasswordService passwordService)
    {
        this.passwordService = passwordService;
    }

    @Autowired
    public void setCredentialsService(StatelessCredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    public User registerUser(User user)
    {
        String publicSalt = credentialsService.generateRandomSalt(32);
        String privateSalt = credentialsService.generateRandomSalt(32);
        String encryptedPassword = credentialsService.encryptPassword(user.getPassword(), publicSalt);
        user.setPassword(encryptedPassword);
        user.setPrivateSalt(privateSalt);
        user.setPublicSalt(publicSalt);
        userDao.save(user);
        return user;
    }

    public User doUserCredentialsMatch(User user, DisposableSaltEntry entry)
    {
        Subject subject = SecurityUtils.getSubject();
        String disposableSalt = loginDisposableSaltCache.get(entry.getTmpId()).toHex();
        if (disposableSalt == null)
        {
            throw new AuthenticationException(
                "The disposable salt can't  be retrieve in current cache.");
        }
        /*
         * 及时清除缓存;
         */
        loginDisposableSaltCache.remove(entry.getTmpId());
        // 解密
        String submittedPlait = EncryptUtils.aesDecrypt(user.getPassword(), disposableSalt);
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),
            submittedPlait);
        subject.login(token);
        return userDao.findByUserName(user.getUserName());
    }

    @Override
    public DisposableSaltEntry getRandomVerifySaltEntry(String preSaltId)
    {
        /*
         * 清除上一次缓存的校验随机盐;
         */
        LOGGER.info("Receive pre salt id [{}] at front end.", preSaltId);
        if (preSaltId != null && !preSaltId.equals("\"\""))
        {
            loginDisposableSaltCache.remove(preSaltId);
            LOGGER.info("Remove pre-salt from cache.");
        }
        ByteSource randomSalt = passwordService.generateRandomSalt(8);
        String tmpId = UUID.randomUUID().toString();
        /*
         * 重新生成校验随机盐到前台
         */
        loginDisposableSaltCache.put(tmpId, randomSalt);
        LOGGER.info("Put disposable salt entry to cache.Id:[{}],Salt:[{}].", tmpId,
            randomSalt.toHex());
        return new DisposableSaltEntry(tmpId, randomSalt.toHex());
    }

    public User fetchUserJoinRolesById(String userId)
    {
        return userDao.fetchUserJoinRolesById(userId);
    }

    @Override
    public String findPasswordByUserName(String userName)
    {
        return userDao.findPasswordByUserName(userName);
    }

    public List<Long> findRoleIdListByUserId(String userId)
    {
        return userDao.findRoleIdListByUserId(userId);
    }

    public Set<String> findPermissionNameSet(String userName)
    {
        return userDao.findPermissionNameSet(userName);
    }

    public void updatePassword(String userName, String newPassword)
        throws DataObjectNotFoundException
    {
        User user = userDao.findByUserName(userName);
        user.setPassword(newPassword);
        userDao.update(user);
    }

    public void correlateRole(String userId, Long roleId)
    {
        List<Long> roleIdList = new ArrayList<Long>();
        roleIdList.add(roleId);
        correlateRoles(userId, roleIdList);
    }

    public void correlateRoles(String userId, List<Long> roleIdList)
    {
        try
        {
            User user = userDao.fetchUserJoinRolesById(userId);
            for (Long roleId : roleIdList)
            {
                user.getRoleSet().add(roleDao.findById(roleId));
            }
            userDao.update(user);
        }
        catch (DataObjectNotFoundException e)
        {
            e.printStackTrace();
        }
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

    public User findByUserName(String userName)
    {
        return userDao.findByUserName(userName);
    }

    @Override
    public User fetchByUserName(String userName)
    {
        return userDao.fetchByUserName(userName);
    }

    public List<Permission> findPermissionByUserName(String userName)
    {
        return userDao.findPermissionByUserName(userName);
    }

    public List<Role> findRolesByUserName(String userName)
    {
        return userDao.findRolesByUserName(userName);
    }

    public Set<String> findRoleNameSetByUserName(String userName)
    {
        return userDao.findRoleNameSetByUserName(userName);
    }

    public void removeRole(String userId, Long roleId)
    {
        List<Long> roleIdList = new ArrayList<Long>();
        roleIdList.add(roleId);
        removeRoles(userId, roleIdList);
    }

    public void removeRoles(String userId, List<Long> roleIdList)
    {
        // List<Role> roleList = roleDao.fetchRoleListByIdList(roleIdList);
        userDao.removeRoles(userId, roleIdList);
    }

    @Override
    public String getPublicSalt(String username)
    {
        return null;
    }

    @Override
    public String fetchPublicSalt(String username)
    {
        try
        {
            return userDao.getPublicSalt(username);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new UnknownAccountException(
                "Unknown account.Please ensure your account is registered and correct.");
        }
    }

    @Override
    public String fetchPrivateSalt(String username)
    {
        try
        {
            return userDao.getPrivateSalt(username);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new UnknownAccountException(
                "Unknown account.Please ensure your account is registered and correct.");
        }
    }

}
