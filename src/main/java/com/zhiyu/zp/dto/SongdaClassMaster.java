
package com.zhiyu.zp.dto;


public class SongdaClassMaster
{
    public static class ClassMaster
    {

        public String getUserId()
        {
            return userId;
        }

        public void setUserId(String userId)
        {
            this.userId = userId;
        }

        public String getPersonName()
        {
            return personName;
        }

        public void setPersonName(String personName)
        {
            this.personName = personName;
        }

        private String userId;
        private String personName;

        public ClassMaster()
        {
        }
    }


    public SongdaClassMaster()
    {
    }

    public String getClassId()
    {
        return classId;
    }

    public void setClassId(String classId)
    {
        this.classId = classId;
    }

    public ClassMaster getHeadTeacher()
    {
        return headTeacher;
    }

    public void setHeadTeacher(ClassMaster headTeacher)
    {
        this.headTeacher = headTeacher;
    }

    private String classId;
    private ClassMaster headTeacher;
}


