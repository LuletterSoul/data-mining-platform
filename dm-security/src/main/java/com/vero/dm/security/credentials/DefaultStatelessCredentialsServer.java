package com.vero.dm.security.credentials;


import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.vero.dm.exception.auth.AccountAcceptedException;
import com.vero.dm.exception.auth.DuplicatedUsernameException;
import com.vero.dm.exception.auth.RegisterInValidException;
import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.model.Student;
import com.vero.dm.model.User;
import com.vero.dm.repository.StudentJpaRepository;
import com.vero.dm.repository.UserJpaRepository;
import com.vero.dm.repository.dto.StudentDto;
import com.vero.dm.repository.dto.UserDto;
import com.vero.dm.security.realm.StatelessInfo;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 12:12 2017/9/14.
 * @since data-mining-platform
 */

@Transactional
@Slf4j
public class DefaultStatelessCredentialsServer extends DefaultPasswordService implements StatelessCredentialsServer
{

    private Mac mac;

    // private UserService userService;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    public final static String MAC_DEFAULT_ALGORITHM = "HmacSHA256";

    public final static String SERCRET_KEY_DEFAULT_ALGORITHM = "HMACSHA256";

    private SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    public DefaultStatelessCredentialsServer()
    {

    }

    // @Autowired
    // @Qualifier("userServiceImpl")
    // public void setUserService(UserService userService)
    // {
    // this.userService = userService;
    // }

    @Override
    public UserDto registerUser(User user)
    {
        String publicSalt = this.generateRandomSalt(32);
        String privateSalt = this.generateRandomSalt(32);
        String encryptedPassword = this.encryptPassword(user.getPassword(), publicSalt);
        user.setPassword(encryptedPassword);
        user.setPrivateSalt(privateSalt);
        user.setPublicSalt(publicSalt);
        userJpaRepository.save(user);
        UserDto userDTO = new UserDto();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public StudentDto registerStudent(Student student)
    {
        String fromStudentId = student.getStudentId();
        String fromPassword = student.getPassword();
        String username = student.getUsername();
        if (StringUtils.isEmpty(fromPassword) || StringUtils.isEmpty(username)
            || StringUtils.isEmpty(fromStudentId))
        {
            throw new RegisterInValidException("注册的密码、用户名、学号不能为空",
                ExceptionCode.RegisterInvalidException);
        }
        log.debug("[{}] post a register request.", username);
        if (userJpaRepository.findUserNames().contains(username))
        {
            log.debug("Duplicated username [{}]", username);
            throw new DuplicatedUsernameException("用户名已被注册", ExceptionCode.DuplicatedUsername);
        }
        // 生成公钥与私钥
        String publicSalt = this.generateRandomSalt(32);
        String privateSalt = this.generateRandomSalt(32);
        // 加密明文密码
        String encryptedPassword = this.encryptPassword(fromPassword, publicSalt);
        if (!StringUtils.isEmpty(fromStudentId))
        {
            // 学生的信息可能由老师提前导入,学生通过注册，领取账号，更新密码和用户名。
            boolean isExist = studentJpaRepository.findAllStudentIds().contains(fromStudentId);
            // 学号在库内不存在，默认注册为新学生
            if (!isExist)
            {
                student.setPrivateSalt(privateSalt);
                student.setPublicSalt(publicSalt);
                student.setPassword(encryptedPassword);
                student.setRegister(true);
                student = studentJpaRepository.save(student);
                log.debug("The request of student [{}] post the un-exist studentId.",
                    student.getStudentName());
            }
            // 学号存在
            else
            {
                Student existStu = studentJpaRepository.findByStudentId(fromStudentId);
                // 已被学生注册领取
                if (existStu.isRegister())
                {
                    log.debug("[{}] was register.", existStu.getUsername());
                    throw new AccountAcceptedException("已被注册", ExceptionCode.AccountAccepted);
                }
                if (StringUtils.isEmpty(existStu.getUsername()))
                {
                    existStu.setUsername(student.getUsername());
                }
                // 未分配密码和用户名的学生替换为注册信息,初始化密码再次加密
                if (StringUtils.isEmpty(existStu.getPassword()))
                {
                    existStu.setPassword(encryptedPassword);
                }
                // 如果已经存在,更新学生的注册信息
                existStu.setStudentName(student.getStudentName());
                existStu.setClassName(student.getClassName());
                existStu.setProfession(student.getProfession());
                existStu.setGrade(student.getGrade());
                existStu.setPrivateSalt(privateSalt);
                existStu.setPublicSalt(publicSalt);
                existStu.setRegister(true);
                student = studentJpaRepository.save(existStu);
            }
        }
        log.debug("[{}]-[{}] register successfully.", student.getStudentId(),
            student.getStudentName());
        return StudentDto.build(student);
    }

    //为管理员导入的学生账号分配密码和用户名
    @Override
    public StudentDto registerImportedStudent(Student student) {
        // 生成公钥与私钥
        String publicSalt = this.generateRandomSalt(32);
        String privateSalt = this.generateRandomSalt(32);
        String fromPassword = student.getPassword();
        // 加密明文密码
        String encryptedPassword = this.encryptPassword(fromPassword, publicSalt);
        student.setPrivateSalt(privateSalt);
        student.setPublicSalt(publicSalt);
        student.setPassword(encryptedPassword);
        student.setRegister(true);
        student = studentJpaRepository.save(student);
        return StudentDto.build(student);
    }

    @Override
    public List<StudentDto> registerImportedStudents(List<Student> students) {
        List<StudentDto> studentDtos = new ArrayList<>();
        students.forEach(s -> studentDtos.add(registerImportedStudent(s)));
        return studentDtos;
    }

    @Override
    public List<StudentDto> registerStudents(List<Student> students)
    {
        List<StudentDto> studentDtos = new ArrayList<>();
        students.forEach(s -> studentDtos.add(registerStudent(s)));
        return studentDtos;
    }

    @Override
    public Hash computeHashWithParams(Object credentials, Map<String, ?> params, int iterations)
    {
        String credential = (String)credentials;
        String paramsString = buildParamsString(params);
        HashRequest request = buildRequest(credential + paramsString);
        return getHashService().computeHash(request);
    }

    @Override
    public String generateRandomSalt(int numBytes)
    {
        return randomNumberGenerator.nextBytes(numBytes).toBase64();
    }

    @Override
    public String encryptPassword(String plaintext, String customSalt)
    {
        HashRequest request = new HashRequest.Builder().setSource(
            plaintext + customSalt).setAlgorithmName(DEFAULT_HASH_ALGORITHM).setIterations(
                1000).build();
        return getHashService().computeHash(request).toBase64();
    }

    @Override
    public String computeNegotiatedApplyToken(String password, String publicSalt)
    {
        HashRequest request = buildRequest(password + publicSalt, 1000);
        return getHashService().computeHash(request).toBase64();
    }

    @Override
    public Hash computeHashWithParams(StatelessInfo info, int iterations)
    {
        String credentials = (String)info.getCredentials();
        String paramsString = buildParamsString(info.getClientParams());
        String serverDigest = digest(credentials, paramsString).toBase64();
        HashRequest request = buildRequest(serverDigest);
        return getHashService().computeHash(request);
    }

    /**
     * 进行简单的SHA-256 的哈希迭代算法
     */
    @Override
    public Hash computeHash(String source)
    {
        HashRequest request = buildRequest(source);
        return getHashService().computeHash(request);
    }

    private HashRequest buildRequest(String source)
    {
        return new HashRequest.Builder().setSource(createByteSource(source)).setAlgorithmName(
            DEFAULT_HASH_ALGORITHM).setIterations(100).build();
    }

    private HashRequest buildRequest(String source, Integer iterations)
    {
        return new HashRequest.Builder().setSource(createByteSource(source)).setAlgorithmName(
            DEFAULT_HASH_ALGORITHM).setIterations(iterations).build();
    }

    @Override
    public Hash computeHash(String source, Integer iterations)
    {
        return getHashService().computeHash(buildRequest(source, iterations));
    }

    /**
     * 使用HMAC -256 算法生成一次消息摘要
     */
    public Hash digest(String message, String salt)
    {
        try
        {
            try
            {
                mac = Mac.getInstance(MAC_DEFAULT_ALGORITHM);
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
            byte[] secretByte = salt.getBytes();
            byte[] dataBytes = message.getBytes();
            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);
            SimpleHash digestHash = new SimpleHash(DefaultPasswordService.DEFAULT_HASH_ALGORITHM);
            digestHash.setBytes(mac.doFinal(dataBytes));
            return digestHash;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public Hash digestMultipleParams(String key, Map<String, ?> map)
    {
        ;
        return digest(key, buildParamsString(map));
    }

    private String buildParamsString(Map<String, ?> map)
    {
        StringBuilder s = new StringBuilder("X-init");
        for (Object values : map.values())
        {
            if (values instanceof String[])
            {
                for (String value : (String[])values)
                {
                    s.append(value);
                }
            }
            else if (values instanceof List)
            {
                for (String value : (List<String>)values)
                {
                    s.append(value);
                }
            }
            else
            {
                s.append(values);
            }
        }
        return s.toString();
    }

}
