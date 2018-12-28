package com.test.springboot.service;


import com.test.springboot.domain.SysUser;
import com.test.springboot.service.base.BaseService;

import java.util.List;

public interface ISysUser extends BaseService<SysUser, Integer> {
    List<SysUser> wirteAndRead(SysUser u) throws Exception;
}