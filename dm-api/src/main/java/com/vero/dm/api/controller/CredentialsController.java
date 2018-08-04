package com.vero.dm.api.controller;


import com.vero.dm.model.Student;
import com.vero.dm.repository.dto.StudentDto;
import com.vero.dm.security.credentials.StatelessCredentialsServer;
import com.vero.dm.security.credentials.UserProfileAccessor;
import com.vero.dm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vero.dm.security.constants.Constants;
import com.vero.dm.security.credentials.DisposableTokenMaintainer;
import com.vero.dm.security.credentials.TokenManager;
import com.vero.dm.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:27 2018/1/23.
 * @since data-mining-platform
 */
@Slf4j
@Profile(value = {"prod","dev","test"})
@RestController
public class CredentialsController
{
    private UserService userService;

    private TokenManager tokenManager;

    private DisposableTokenMaintainer tokenMaintainer;

    private UserProfileAccessor profileAccessor;

    private StatelessCredentialsServer credentialsServer;

    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setTokenMaintainer(DisposableTokenMaintainer tokenMaintainer)
    {
        this.tokenMaintainer = tokenMaintainer;
    }

    @Autowired
    public void setProfileAccessor(UserProfileAccessor profileAccessor) {
        this.profileAccessor = profileAccessor;
    }

    @Autowired
    public void setCredentialsServer(StatelessCredentialsServer credentialsServer) {
        this.credentialsServer = credentialsServer;
    }

    @Autowired
    public void setTokenManager(TokenManager tokenManager)
    {
        this.tokenManager = tokenManager;
    }

    @Autowired
    @Qualifier("userServiceImpl")
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @ApiOperation("申请访问令牌")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "timestamp", value = "请求时间戳", dataType = "String", paramType = "header", required = true),
        @ApiImplicitParam(name = "providedCredential", value = "认证信息", dataType = "String", paramType = "header", required = true)})
    @GetMapping(value = "/tokens/{username}")
    public String getExpiredToken(@RequestHeader(Constants.TIMESTAMP_HEADER) String timestamp,
                                  @RequestHeader(Constants.APPLY_CREDENTIAL) String providedCredential,
                                  @PathVariable("username") String username,
                                  HttpServletResponse response)
    {
        return tokenManager.applyExpiredToken(username, providedCredential, timestamp, response);
    }


    @ApiOperation("创建一个学生账户")
    @PostMapping(value = "/stu_accounts")
    public ResponseEntity<StudentDto> create(@RequestBody Student student)
    {
        StudentDto d = credentialsServer.registerStudent(student);
        Student newStu = studentService.findByStudentId(d.getStudentId());
        List<String> roleNames = new ArrayList<>();
        //为每个学生分配学生角色，赋予访问权限
        roleNames.add("student");
        userService.correlateRoles(newStu.getUserId(), roleNames);
        return new ResponseEntity<>(d, HttpStatus.CREATED);
    }

    @ApiOperation("重复的用户名将创建失败，用于账户创建时的重复校验")
    @GetMapping(value = "/stu_accounts")
    public boolean create(@RequestParam("username") String username)
    {
        return userService.createUsername(username);
    }

    @ApiOperation("删除所有Token信息(用于微服务注销)")
    @DeleteMapping(value = "/tokens")
    public String deleteToken(@ApiParam(value = "访问令牌") @RequestHeader(value = Constants.ACCESS_TOKEN_HEADER,required = false) String accessToken)
    {
        if(!Objects.isNull(accessToken)){
            tokenManager.cleanTokenCache(accessToken);
            tokenMaintainer.cleanTokenList(accessToken);
        }
        return accessToken;
    }

    @Cacheable(cacheNames = "userPublicSaltCache")
    @ApiOperation("根据用户名获取公盐")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "path", required = true)})
    @GetMapping(value = "/public_salts/{username}")
    public String publicSalt(@PathVariable("username") String username)
    {
        return userService.fetchPublicSalt(username);
    }
}
