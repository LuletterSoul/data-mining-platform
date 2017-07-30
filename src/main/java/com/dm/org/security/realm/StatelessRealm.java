//package com.dm.org.security.realm;
//
//
//import com.dm.org.model.User;
//import com.dm.org.security.HmacSHA256Utils;
//import com.dm.org.service.UserService;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.util.ByteSource;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
///**
// * @author 刘祥德 qq313700046@icloud.com .
// * @date created in 11:52 2017/7/17.
// * @description
// * @modified by:
// */
//public class StatelessRealm extends AuthorizingRealm
//{
//    private UserService userService;
//
//    @Autowired
//    public void setUserService(UserService userService)
//    {
//        this.userService = userService;
//    }
//
//    public boolean supports(AuthenticationToken token)
//    {
//        // 仅支持StatelessToken类型的Token
//        return token instanceof StatelessToken;
//    }
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
//    {
//        String username = (String)principals.getPrimaryPrincipal();
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.setRoles(userService.findRoleNameSetByUserName(username));
//        authorizationInfo.setStringPermissions(userService.findPermissionNameSet(username));
//        return authorizationInfo;
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
//        throws AuthenticationException
//    {
//        StatelessToken statelessToken = (StatelessToken)token;
//        String username = statelessToken.getUsername();
////        String key = getKey(username);// 根据用户名获取密钥（和客户端的一样）
//        User user = userService.findByUserName(username);
//        // 在服务器端生成客户端参数消息摘要
//       // String serverDigest = HmacSHA256Utils.digest(key, statelessToken.getParams());
//        // 然后进行客户端消息摘要和服务器端消息摘要的匹配
//       // return new SimpleAuthenticationInfo(username, serverDigest, getName());
//        return new SimpleAuthenticationInfo(user.getUserName(), // 用户名
//                user.getPassword(), // 密码
//                ByteSource.Util.bytes(user.getCredentialsSalt()), // saltEntry=username+saltEntry
//                getName() // realm name
//        );
//    }
//
//    private String getKey(String username)
//    {// 得到密钥，此处硬编码一个
//        if ("admin".equals(username))
//        {
//            return "dadadswdewq2ewdwqdwadsadasd";
//        }
//        return null;
//    }
//}