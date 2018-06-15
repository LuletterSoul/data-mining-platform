package com.vero.dm.repository.dto;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.vero.dm.model.User;
import com.vero.dm.model.UserAccessStatus;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Data
public class UserDto implements Serializable
{
    private String userId;

    private String username;

    private String name;

    private String avatar;

    private String gender;

    private String regionCode;

    private String introduction;

    private Date birthday;

    private UserAccessStatus accessStatus;

    private Date lastLoginTime;

    private List<String> roles;

    private List<String> permissions;

    public UserDto(String userId, String username, String name, String avatar, String gender,
                   String regionCode, String introduction, UserAccessStatus accessStatus,
                   Timestamp lastLoginTime)
    {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
        this.regionCode = regionCode;
        this.introduction = introduction;
        this.accessStatus = accessStatus;
        this.lastLoginTime = new Date(lastLoginTime.getTime());
    }

    public UserDto()
    {}

    public static UserDto build(User user)
    {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        userDto.setAvatar(new String(user.getAvatar()));
        userDto.setGender(user.getGender());
        userDto.setIntroduction(user.getIntroduction());
        userDto.setRegionCode(user.getRegionCode());
        userDto.setAccessStatus(user.getAccountStatus());
        userDto.setBirthday(user.getBirthday());
        userDto.setLastLoginTime(new Date(user.getLastLoginTime().getTime()));
        return userDto;
    }

    public static UserDto build(User user, List<String> roleNames, List<String> permissionNames)
    {
        UserDto userDto = build(user);
        userDto.setRoles(roleNames);
        userDto.setPermissions(permissionNames);
        return userDto;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", introduction='" + introduction + '\'' +
                ", birthday=" + birthday +
                ", accessStatus=" + accessStatus +
                ", lastLoginTime=" + lastLoginTime +
                ", roles=" + roles +
                ", permissions=" + permissions +
                '}';
    }
}
