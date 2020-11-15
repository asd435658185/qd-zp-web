package com.zhiyu.zp.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * @author wdj
 *
 */

public class JavaBeanPerson {

	private String name;    // 姓名  
    private String sex;        // 性别  
    private int age;        // 年龄  
    private String hometown;// 籍贯  
    private String phone;    // 电话号码  
    
    private Integer no;
    private String score;
    
    private List<JavaBeanPerson> thirdLevelData = Lists.newArrayList();
      
    public JavaBeanPerson() {}  
      
    public JavaBeanPerson(String name, String sex, int age, String hometown, String phone,String score) {  
        this.name = name;  
        this.sex = sex;  
        this.age = age;  
        this.hometown = hometown;  
        this.phone = phone;  
        this.score = score;
    }  
    
    public JavaBeanPerson(String name, String sex, int age, String hometown, String phone,Integer no) {  
        this.name = name;  
        this.sex = sex;  
        this.age = age;  
        this.hometown = hometown;  
        this.phone = phone;  
        this.no = no;
    }  
      
    // 此处省略字段的getter和setter  
      
    public static List<JavaBeanPerson> getList() {  
        List<JavaBeanPerson> list = new ArrayList<JavaBeanPerson>();  
        list.add(new JavaBeanPerson("Lily", "female", 22, "Hubei", "10086","2"));  
        list.add(new JavaBeanPerson("麻花", "male", 33, "Beijing", "13800000000","1"));  
        list.add(new JavaBeanPerson("马云", "male", 44, "HongKong", "13812345678","2"));  
        list.add(new JavaBeanPerson("欧阳正华", "female", 28, "Guangxi", "18677778888","1"));  
        list.add(new JavaBeanPerson("Jessie", "female", 26, "Gansu", "18219177720","1"));  
        
        for(int i=0;i<list.size();i++){
        	JavaBeanPerson p = list.get(i);
        	if(i==0){
        		p.getThirdLevelData().add(new JavaBeanPerson("pLily", "female", 22, "Hubei", "10086",1));
            	p.getThirdLevelData().add(new JavaBeanPerson("Macro", "male", 33, "Beijing", "13800000000",2));
            	p.getThirdLevelData().add(new JavaBeanPerson("Andy", "male", 44, "HongKong", "13812345678",3));
            	p.getThirdLevelData().add(new JavaBeanPerson("Linder", "female", 28, "Guangxi", "18677778888",4));
            	p.getThirdLevelData().add(new JavaBeanPerson("pLily", "female", 22, "Hubei", "10086",5));
        	}else{
        		p.getThirdLevelData().add(new JavaBeanPerson("pLily", "female", 22, "Hubei", "10086",6));
            	p.getThirdLevelData().add(new JavaBeanPerson("Macro", "male", 33, "Beijing", "13800000000",7));
            	p.getThirdLevelData().add(new JavaBeanPerson("Andy", "male", 44, "HongKong", "13812345678",8));
        	}
        	
        }
        return list;  
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public List<JavaBeanPerson> getThirdLevelData() {
		return thirdLevelData;
	}

	public void setThirdLevelData(List<JavaBeanPerson> thirdLevelData) {
		this.thirdLevelData = thirdLevelData;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}  
    
    
}
