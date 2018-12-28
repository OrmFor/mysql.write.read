package com.test.springboot.domain.entity;

import com.test.springboot.domain.entity.vo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class SysTaskHandelEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String moduleName;
	private String hostname;
	private String ipAddress;
	private Boolean isEnabled;
	private String remark;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updatedAt;

    public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
    public String getModuleName() {
        return moduleName;
    }
	public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    public String getHostname() {
        return hostname;
    }
	public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    public String getIpAddress() {
        return ipAddress;
    }
	public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public Boolean getIsEnabled() {
        return isEnabled;
    }
	public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    public String getRemark() {
        return remark;
    }
	public void setRemark(String remark) {
        this.remark = remark;
    }
    public java.util.Date getCreatedAt() {
        return createdAt;
    }
	public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }
	public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}