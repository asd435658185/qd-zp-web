/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.zhiyu.zp.dto;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dto.SongdaUserRoleDto.SongdaRole;

import java.util.List;

// Referenced classes of package com.zhiyu.platform.data.manage.common.dto:
//            SongdaUserRoleDto

public class SongdaSchoolTeacherDto
{
    public static class Position
    {

        public String getPositionId()
        {
            return positionId;
        }

        public void setPositionId(String positionId)
        {
            this.positionId = positionId;
        }

        public String getPositionName()
        {
            return positionName;
        }

        public void setPositionName(String positionName)
        {
            this.positionName = positionName;
        }

        private String positionId;
        private String positionName;

        public Position()
        {
        }
    }


    public SongdaSchoolTeacherDto()
    {
        positions = Lists.newArrayList();
        roles = Lists.newArrayList();
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserNo()
    {
        return userNo;
    }

    public void setUserNo(String userNo)
    {
        this.userNo = userNo;
    }

    public String getPersonName()
    {
        return personName;
    }

    public void setPersonName(String personName)
    {
        this.personName = personName;
    }

    public String getPinyName()
    {
        return pinyName;
    }

    public void setPinyName(String pinyName)
    {
        this.pinyName = pinyName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getUserHead()
    {
        return userHead;
    }

    public void setUserHead(String userHead)
    {
        this.userHead = userHead;
    }

    public String getIdcard()
    {
        return idcard;
    }

    public void setIdcard(String idcard)
    {
        this.idcard = idcard;
    }

    /**
     * @deprecated Method getPositions is deprecated
     */

    public List<Position> getPositions()
    {
        return positions;
    }

    public void setPositions(List<Position> positions)
    {
        this.positions = positions;
    }

    public List<SongdaRole> getRoles()
    {
        return roles;
    }

    public void setRoles(List<SongdaRole> roles)
    {
        this.roles = roles;
    }

    private String userId;
    private String userName;
    private String userNo;
    private String personName;
    private String pinyName;
    private String gender;
    private String userHead;
    private String idcard;
    private List<Position> positions;
    private List<SongdaRole> roles;
}


/*
	DECOMPILATION REPORT

	Decompiled from: E:\repository\com\zhiyu\platform\data-manage-common\0.0.1-SNAPSHOT\data-manage-common-0.0.1-SNAPSHOT.jar
	Total time: 39 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/