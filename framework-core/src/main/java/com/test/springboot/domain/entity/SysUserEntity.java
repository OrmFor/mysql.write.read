package com.test.springboot.domain.entity;


import com.test.springboot.domain.entity.vo.BaseModelBean;

public class SysUserEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userName;
	
    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
	public void setUserName(String userName) {
        this.userName = userName;
    }
}