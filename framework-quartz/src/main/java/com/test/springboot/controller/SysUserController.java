package com.test.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.springboot.controller.base.BaseController;
import com.test.springboot.core.ResultCode;
import com.test.springboot.domain.SysUser;
import com.test.springboot.service.ISysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller("admin_SysUserController")
@RequestMapping("/admin/sysUser")
public class SysUserController extends BaseController {
    private static final String MODULE_NAME = "sysUser";// TODO

    @Autowired
    private ISysUser sysUserService;


    @RequestMapping("/findById")
    @ResponseBody
    public ResultCode findById(){
        JSONObject json = getJsonFromRequest();
        Integer  id = json.getInteger("id");
        SysUser u = sysUserService.selectByPrimaryKey(id);
        return new ResultCode("0","success",u);
    }

    @RequestMapping(value = "/add",method=RequestMethod.GET)
    public String add(Model model, Integer id) {
        if(id != null){
            SysUser bean = sysUserService.selectByPrimaryKey(id);
            model.addAttribute("bean", bean);
        }
        return "admin/sysUser_add";
    }

    @RequestMapping(value = "/view",method=RequestMethod.GET)
    public String view(Model model, Integer id) {
        if(id != null){
            SysUser bean = sysUserService.selectByPrimaryKey(id);
            model.addAttribute("bean", bean);
        }
        return "admin/sysUser_view";
    }
	
    @RequestMapping(value = "/info",method=RequestMethod.POST)
    @ResponseBody
    public SysUser info(Integer id) {
    	if(id == null){
    		return new SysUser();
    	}
    	SysUser bean = sysUserService.selectByPrimaryKey(id);
    	if(bean == null){
    		return new SysUser();
    	}
    	return bean;
    }

    @RequestMapping(value = "/save",method=RequestMethod.POST)
    @ResponseBody
    public ResultCode save (SysUser bean) {
        if (bean == null) {
            return new ResultCode("100", "noData");
        }
        if (bean.getId() == null) {
            //添加
            sysUserService.insertSelective(bean);
        } else {
            //修改
            sysUserService.updateByPrimaryKeySelective(bean);
        }
        return new ResultCode("0", "success");
    }

    @RequestMapping(value = "/del",method=RequestMethod.POST)
    @ResponseBody
    public ResultCode del (Integer id) {
        SysUser bean = sysUserService.selectByPrimaryKey(id);
        if (bean == null) {
          return new ResultCode("100", "noData");
        }
        sysUserService.deleteByPrimaryKey(id);
        return new ResultCode("0", "success");
    }

    /**
     * 测试写然后读
     * @return
     */
    @RequestMapping("/addAndRead")
    @ResponseBody
    public ResultCode addAndRead(){
        JSONObject jsonObject = getJsonFromRequest();
        SysUser u = new SysUser();
       // u.setId(id);
        u.setUserName(jsonObject.getString("userName"));

        List<SysUser> list = null;
        try {
            list = sysUserService.wirteAndRead(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultCode("0","success",list);
    }
}