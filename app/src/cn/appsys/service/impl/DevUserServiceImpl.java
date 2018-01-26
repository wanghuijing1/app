package cn.appsys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.appsys.controller.developer.DevUserLoginController;
import cn.appsys.dao.devuser.DevUserMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;
@Service
public class DevUserServiceImpl implements DevUserService {

	private Logger logger=Logger.getLogger(DevUserLoginController.class);
	@Resource
	private DevUserMapper devusermapper;
	@Override
	public DevUser login(String devCode, String devPassword) throws Exception {
		DevUser user=null;
		user=devusermapper.getLoginUser(devCode);
		if (null!=user) {//登陆成功
			//验证密码
			if (!devPassword.equals(user.getDevPassword())) {
				user=null;
			}
		}
		return user;
	}
	@Override
	public List<DataDictionary> getDictionary(String typeCode) throws Exception {
		List<DataDictionary> list=devusermapper.getDictionary(typeCode);
		return list;
	}
	
	@Override
	public List<AppCategory> getType(int parentId) throws Exception {
		List<AppCategory> list=devusermapper.getType(parentId);
		System.out.println("===>"+parentId+"   "+list.size());
		return list;
	}
	@Override
	public PageSupport getappinfolistselect(AppInfo appinfo, int currentPageNo,
			int pageSize) throws Exception {
		List<AppInfo> list=null;
		list=devusermapper.getappinfolistselect(appinfo, (currentPageNo-1)*pageSize, pageSize);
		PageSupport page=new PageSupport();
		page.setCurrentPageNo(currentPageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(count(appinfo));
		page.setList(list);
		return page;
	}
	@Override
	public int count(AppInfo appinfo) {
		int count=devusermapper.count(appinfo);
		return count;
	}

	
}
