/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.zhiyu.zp.dto;

import java.util.List;


public class SongdaUserRoleDto
{
    public static class SongdaRole
    {

        public String getRoleId()
        {
            return roleId;
        }

        public void setRoleId(String roleId)
        {
            this.roleId = roleId;
        }

        public String getRoleName()
        {
            return roleName;
        }

        public void setRoleName(String roleName)
        {
            this.roleName = roleName;
        }

        private String roleId;
        private String roleName;

        public SongdaRole()
        {
        }
    }


    public SongdaUserRoleDto()
    {
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
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
    private List<SongdaRole> roles;
}


