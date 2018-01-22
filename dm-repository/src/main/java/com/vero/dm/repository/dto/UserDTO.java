package com.vero.dm.repository.dto;


import java.sql.Date;
import java.sql.Timestamp;

import com.vero.dm.model.UserAccessStatus;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Data
public class UserDTO
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

    public UserDTO(String userId, String username, String name, String avatar, String gender,
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

    public UserDTO()
    {}

    // public static UserDTO build(User user) {
    //// UserDTO userDTO = new UserDTO();
    //// userDTO.setUserId(user.getUserId());
    //// userDTO.setUsername(user.getUsername());
    //// userDTO.setName(user.getName());
    //// userDTO.setAvatar(user.getAvatar());
    //// userDTO.setBirthday(user.getBirthday());
    //// userDTO.setGender(user.getGender());
    //// userDTO.setIntroduction(user.getIntroduction());
    //// userDTO.setRegionCode(user.getRegionCode());
    //// userDTO.setAccessStatus(user.getAccountStatus());
    //// userDTO.setLastLoginTime(new Date(user.getLastLoginTime().getTime()));
    //// return userDTO;
    // }

}
