package com.dm.org.dto;

import com.dm.org.enums.UserAccessStatus;
import com.dm.org.model.User;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

public class UserDTO  {
    private String userId;
    private String username;
    private String name;
    private byte[] avatar;
    private String gender;
    private String regionCode;
    private String introduction;
    private Date birthday;
    private UserAccessStatus accessStatus;
    private Date lastLoginTime;

    public UserDTO(String userId, String username,String name, byte[] avatar, String gender, String regionCode, String introduction, UserAccessStatus accessStatus,Timestamp lastLoginTime) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
        this.regionCode = regionCode;
        this.introduction = introduction;
        this.accessStatus = accessStatus;
        this.lastLoginTime =new Date(lastLoginTime.getTime());
    }

    public UserDTO() {
    }

    public static UserDTO build(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setGender(user.getGender());
        userDTO.setIntroduction(user.getIntroduction());
        userDTO.setRegionCode(user.getRegionCode());
        userDTO.setAccessStatus(user.getAccountStatus());
        userDTO.setLastLoginTime(new Date(user.getLastLoginTime().getTime()));
        return userDTO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public UserAccessStatus getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(UserAccessStatus accessStatus) {
        this.accessStatus = accessStatus;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
