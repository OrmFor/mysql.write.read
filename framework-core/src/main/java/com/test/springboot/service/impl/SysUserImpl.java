package com.test.springboot.service.impl;


import com.test.springboot.annotation.WriteDataSource;
import com.test.springboot.dao.SysUserMapper;
import com.test.springboot.domain.SysUser;
import com.test.springboot.service.ISysUser;
import com.test.springboot.service.base.BaseServiceImpl;
import com.test.springboot.service.base.jms.JmsProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("sysUser")
public class SysUserImpl extends BaseServiceImpl<SysUser, SysUserMapper, Integer> implements ISysUser {
    @Autowired
    private SysUserMapper sysUserMapper;

    protected SysUserMapper getDao() {
        return sysUserMapper;
    }

    @Autowired
    private JmsProducer producer;

    @Override
    @WriteDataSource
   // @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SysUser> wirteAndRead(SysUser u) throws Exception {
        sysUserMapper.insert(u);
        List<SysUser> list = this.getList();
        int i = 0;
        for(SysUser sysUser : list){
            SysUser condi = new SysUser();
            condi.setId(sysUser.getId());
            sysUser.setUserName(sysUser.getUserName()+"9");
            this.updateByCondition(sysUser,condi);
            if(i==3){
                producer.sendMsg("testext.error","insert第"+i+"条error,数据回滚");

                throw new Exception("error");
            }
            producer.sendMsg("testext.insert","insert第"+i+"条");
            i++;
        }

        return this.getList();
    }
}