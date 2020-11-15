package com.zhiyu.zp.dto;
/**
 * 
 * @author wdj
 *
 */
public class SongdaLoginUserInfo
{

    public SongdaLoginUserInfo(String userId, String userNo, String userName, String mobile, String userHead, String personName, String identity)
    {
        this.userId = userId;
        this.userNo = userNo;
        this.userName = userName;
        this.mobile = mobile;
        this.userHead = userHead;
        this.personName = personName;
        this.identity = identity;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserNo()
    {
        return userNo;
    }

    public void setUserNo(String userNo)
    {
        this.userNo = userNo;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getUserHead()
    {
        return userHead;
    }

    public void setUserHead(String userHead)
    {
        this.userHead = userHead;
    }

    public String getPersonName()
    {
        return personName;
    }

    public void setPersonName(String personName)
    {
        this.personName = personName;
    }

    public String getIdentity()
    {
        return identity;
    }

    public void setIdentity(String identity)
    {
        this.identity = identity;
    }

    private String userId;
    private String userNo;
    private String userName;
    private String mobile;
    private String userHead;
    private String personName;
    private String identity;
}
